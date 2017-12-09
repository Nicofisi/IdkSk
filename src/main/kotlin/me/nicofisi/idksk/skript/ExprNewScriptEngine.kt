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


@Suppress("UNCHECKED_CAST")
class ExprNewScriptEngine : SimpleExpression<ScriptEngine>() {
    companion object {
        init {
            Skript.registerExpression(ExprNewScriptEngine::class.java, ScriptEngine::class.java, ExpressionType.SIMPLE,
                    "new %string% script[ing] engine", "new script[ing] engine (of|with|using) lang[uage] %string%")
        }
    }

    private var name: Expression<String>? = null

    override fun getReturnType(): Class<out ScriptEngine> {
        return ScriptEngine::class.java
    }

    override fun init(exprs: Array<out Expression<*>>, mp: Int, id: Kleenean?, pr: SkriptParser.ParseResult?): Boolean {
        name = exprs[0] as Expression<String>
        return true
    }

    override fun get(e: Event?): Array<ScriptEngine>? {
        val name = name?.getSingle(e)
        return if (name == null) null else arrayOf(ScriptEngineManager().getEngineByName(name))
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "idk new script engine expr"
    }

}