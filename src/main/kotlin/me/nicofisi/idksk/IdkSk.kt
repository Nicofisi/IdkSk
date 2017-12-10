package me.nicofisi.idksk

import ch.njol.skript.Skript
import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.registrations.Classes
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.mcstats.Metrics
import java.io.File
import java.net.URL
import javax.script.ScriptEngine
import javax.script.ScriptException

class IdkSk : JavaPlugin() {
    companion object {
        var instance: IdkSk? = null
    }

    var downloadedJarVersion: Int = 0

    override fun onEnable() {
        instance = this
        logger.info("The best unnamed addon created so far is loading, but you already know that from the line above right?")

        if (Bukkit.getPluginManager().getPlugin("Skript") == null) {
            logger.severe("Loool m8, where tf is Skript? Put it on your server and come back")
            return
        }

        Skript.registerAddon(this).loadClasses("me.nicofisi.idksk.skript")

        Classes.registerClass(ClassInfo(ScriptEngine::class.java, "scriptengine")
                .name("script engine")
                .user("script engine", "scriptengine", "scripting engine")
        )

        Classes.registerClass(ClassInfo(ScriptException::class.java, "scriptexception")
                .name("script exception")
                .user("script exception", "scriptexception", "scripting exception")
        )

        Metrics(this).start()

        logger.info("I find it kind of surprising, but everything loaded successfully!")

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, {
            val latestInfoUrl = URL("https://nicofi.si/idksk/latest-version.txt")
            val latestInfo = IOUtils.toString(latestInfoUrl, "UTF-8")
            val latestVer = latestInfo.substring(0, latestInfo.indexOf(" ")).toInt()
            val currentVer = description.permissions.map { it.description.trim() }
                    .find { it.startsWith("int-version ") }?.substring("int-version ".length)?.toInt() ?: 1
            if (currentVer >= latestVer || latestVer == downloadedJarVersion) return@runTaskTimerAsynchronously // everything is fine
            val downloadUrl = latestInfo.substring(latestInfo.indexOf(" ") + 1)
            logger.info("Downloading an update for IdkSk from $downloadUrl")
            val tempFile = File.createTempFile("IdkSk", "jar")
            FileUtils.copyURLToFile(URL(downloadUrl), tempFile)
            val currentJarFile = File(javaClass.protectionDomain.codeSource.location.toURI())
            currentJarFile.delete()
            currentJarFile.createNewFile()
            FileUtils.copyFile(tempFile, currentJarFile)
            tempFile.delete()
            downloadedJarVersion = latestVer
            logger.info("Hmm.. Thats it! Restart the server for the update to work")
        }, 20 * 15, 28 * 60 * 90)
    }

    override fun onDisable() {
        logger.info("Idk bai")
    }
}