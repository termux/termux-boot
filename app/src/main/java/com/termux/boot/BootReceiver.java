package com.termux.boot;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;

        @SuppressLint("SdCardPath") final String BOOT_SCRIPT_PATH = "/data/data/com.termux/files/home/.termux/boot";
        final File BOOT_SCRIPT_DIR = new File(BOOT_SCRIPT_PATH);
        File[] files = BOOT_SCRIPT_DIR.listFiles();
        if (files == null) files = new File[0];

        // Sort files so that they get executed in a repeatable and logical order.
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                return f1.getName().compareTo(f2.getName());
            }
        });

        StringBuilder logMessage = new StringBuilder();
        for (File file : files) {
            if (!file.isFile()) return;

            if (logMessage.length() > 0) logMessage.append(", ");
            logMessage.append(file.getName());

            ensureFileReadableAndExecutable(file);

            PersistableBundle extras = new PersistableBundle();
            extras.putString(BootJobService.SCRIPT_FILE_PATH, file.getAbsolutePath());

            ComponentName serviceComponent = new ComponentName(context, BootJobService.class);
            JobInfo job = new JobInfo.Builder(0, serviceComponent)
                    .setExtras(extras)
                    .setOverrideDeadline(3 * 1000)
                    .build();
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            assert jobScheduler != null;
            jobScheduler.schedule(job);
        }

        if (logMessage.length() > 0) {
            Log.i("termux", "Executed files at boot: " + logMessage);
        } else {
            Log.i("termux", "No files to execute at boot");
        }
    }

    /** Ensure readable and executable file if user forgot to do so. */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void ensureFileReadableAndExecutable(File file) {
        if (!file.canRead()) file.setReadable(true);
        if (!file.canExecute()) file.setExecutable(true);
    }

}
