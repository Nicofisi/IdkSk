package me.nicofisi.idksk.skript

import ch.njol.skript.Skript
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.ExpressionType
import ch.njol.skript.lang.SkriptParser
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.util.Kleenean
import org.bukkit.event.Event
import javax.script.ScriptEngine
import javax.script.ScriptException

@Suppress("UNCHECKED_CAST")
class ExprEvalScriptEngine : SimpleExpression<Any>() {
    companion object {
        init {
            Skript.registerExpression(ExprEvalScriptEngine::class.java, Any::class.java, ExpressionType.SIMPLE,
                    "(eval|run|execute|exec|evaluate) [code] %strings% (using|with) [script[ ]engine] %scriptengine% [and (get|return) [the] result]")
        }
    }

    private var code: Expression<String>? = null
    private var engine: Expression<ScriptEngine>? = null

    override fun getReturnType(): Class<out Any> {
        return Object::class.java
    }

    override fun init(exprs: Array<out Expression<*>>, mp: Int, id: Kleenean?, pr: SkriptParser.ParseResult?): Boolean {
        code = exprs[0] as Expression<String>
        engine = exprs[1] as Expression<ScriptEngine>
        return true
    }

    override fun get(e: Event?): Array<Any>? {
        val code = code?.getArray(e)
        val engine = engine?.getSingle(e)
        if (code == null || engine == null) return null
        return try {
            arrayOf(engine.eval(java.lang.String.join("\n", code.asIterable())))
        } catch (t: ScriptException) {
            arrayOf(t)
        }
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "idk new script engine expr"
    }

}