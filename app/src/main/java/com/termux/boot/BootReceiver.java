package com.termux.boot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class BootReceiver extends WakefulBroadcastReceiver {

    // Constants from TermuxService.
    private static final String TERMUX_SERVICE = "com.termux.app.TermuxService";
    private static final String ACTION_EXECUTE = "com.termux.service_execute";
    private static final String EXTRA_EXECUTE_IN_BACKGROUND = "com.termux.execute.background";

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
            Uri scriptUri = new Uri.Builder().scheme("com.termux.file").path(file.getAbsolutePath()).build();

            Intent executeIntent = new Intent(ACTION_EXECUTE, scriptUri);
            executeIntent.setClassName("com.termux", TERMUX_SERVICE);
            executeIntent.putExtra(EXTRA_EXECUTE_IN_BACKGROUND, true);

            startWakefulService(context, executeIntent);
        }

        if (logMessage.length() > 0) {
            Log.i("termux-boot", "Executed files at boot: " + logMessage);
        } else {
            Log.i("termux-boot", "No files to execute at boot");
        }
    }

    /** Ensure readable and executable file if user forgot to do so. */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void ensureFileReadableAndExecutable(File file) {
        if (!file.canRead()) file.setReadable(true);
        if (!file.canExecute()) file.setExecutable(true);
    }

}
