package me.nicofisi.idksk.skript

import ch.njol.skript.Skript
import ch.njol.skript.lang.Effect
import ch.njol.skript.lang.Expression
import ch.njol.skript.lang.SkriptParser
import ch.njol.util.Kleenean
import org.bukkit.event.Event
import javax.script.ScriptEngine

@Suppress("UNCHECKED_CAST")
class EffPassToScriptEngine : Effect() {
    private var objects: Expression<Any>? = null
    private var name: Expression<String>? = null
    private var engine: Expression<ScriptEngine>? = null

    companion object {
        init {
            Skript.registerEffect(EffPassToScriptEngine::class.java,
                    "(pass|add) [(arg[ument]|param[eter]|var[iable]|obj[ect])] %objects% (named|with name) %string% to [script[ ]engine] %scriptengine%")
        }
    }

    override fun init(exprs: Array<out Expression<*>>, mp: Int, del: Kleenean?, pr: SkriptParser.ParseResult?): Boolean {
        objects = exprs[0] as Expression<Any>
        name = exprs[1] as Expression<String>
        engine = exprs[2] as Expression<ScriptEngine>
        return true
    }

    override fun execute(e: Event?) {
        // val objects = if (objects?.isSingle != false /*kotlin*/) objects?.getSingle(e) else objects?.getArray(e)
        val objects = objects!!.getArray(e)
        val name = name!!.getSingle(e)
        val engine = engine!!.getSingle(e)
        if (objects == null || name == null || engine == null) return
        engine.put(name, if (objects.size == 1) objects[0] else objects)
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "pass argument"
    }
}