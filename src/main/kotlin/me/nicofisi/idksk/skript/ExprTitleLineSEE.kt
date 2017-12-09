package me.nicofisi.idksk.skript

import ch.njol.skript.Skript
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.ExpressionType
import java.io.PrintWriter
import java.io.StringWriter
import javax.script.ScriptException

@Suppress("UNCHECKED_CAST")
class ExprTitleLineSEE : SimplePropertyExpression<ScriptException, String>() {
    companion object {
        init {
            Skript.registerExpression(ExprTitleLineSEE::class.java, String::class.java, ExpressionType.PROPERTY,
                    "title line of [stack[ ]trace] %scriptexception%", " [stack[ ]trace] %scriptexception%'s title line")
        }
    }

    override fun getPropertyName(): String {
        return "stacktrace"
    }

    override fun convert(sex: ScriptException?): String? {
        return if (sex == null) null else {
            sex.cause?.javaClass?.canonicalName + ": " + sex.cause?.message
        }
    }

    override fun getReturnType(): Class<out String> {
        return String::class.java
    }
}