package me.nicofisi.idksk

import ch.njol.skript.Skript
import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.registrations.Classes
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class IdkSk : JavaPlugin() {
    companion object {
        var instance: IdkSk? = null
    }

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

        logger.info("I find it kind of surprising, but everything loaded successfully!")
    }

    override fun onDisable() {
        logger.info("Idk bai")
    }
}