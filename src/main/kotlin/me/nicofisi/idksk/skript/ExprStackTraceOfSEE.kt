package me.nicofisi.idksk.skript

import ch.njol.skript.Skript
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import org.bukkit.event.Event
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import java.io.PrintWriter
import java.io.StringWriter
import javax.script.ScriptException

@Suppress("UNCHECKED_CAST")
class ExprStackTraceOfSEE : SimplePropertyExpression<ScriptException, String>() {
    companion object {
        init {
            Skript.registerExpression(ExprStackTraceOfSEE::class.java, String::class.java, ExpressionType.PROPERTY,
                    "stack[ ]trace of %scriptexception%", "%scriptexception%'s stack[ ]trace")
        }
    }

    override fun getPropertyName(): String {
        return "stacktrace"
    }

    override fun convert(sex: ScriptException?): String? {
        return if (sex == null) null else {
            val sw = StringWriter()
            val pw = PrintWriter(sw)
            sex.cause?.printStackTrace(pw)
            sw.toString()
        }
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }
}