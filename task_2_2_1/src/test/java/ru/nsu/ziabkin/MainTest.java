package ru.nsu.ziabkin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Integration test of the main class.
 */
class MainTest {

    @Test
    void testMainExecutionWithConfig() {
        File configFile = new File("config.json");
        boolean createdManually = false;

        if (!configFile.exists()) {
            try (FileWriter writer = new FileWriter(configFile)) {
                writer.write("{\"warehouseCapacity\": 5, \"bakerSpeeds\": [100], "
                        + "\"couriers\": [{\"trunkSize\": 2, \"speedMs\": 100}]}");
                createdManually = true;
            } catch (IOException e) {
                Assertions.fail("Failed to create temporary config.json for test");
            }
        }

        Assertions.assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        }, "Main method should execute without exceptions when config is present");

        if (createdManually) {
            configFile.delete();
        }
    }

    @Test
    void testMainDoesNotCrashWhenConfigMissing() {
        File configFile = new File("config.json");
        File tempFile = new File("config_backup.json");

        boolean moved = false;
        if (configFile.exists()) {
            moved = configFile.renameTo(tempFile);
        }

        Assertions.assertDoesNotThrow(() -> {
            Main.main(new String[]{});
        }, "Main should handle missing config.json gracefully without throwing exceptions");

        if (moved) {
            tempFile.renameTo(configFile);
        }
    }
}