package me.nicofisi.idksk.skript

import ch.njol.skript.Skript
import ch.njol.skript.classes.ClassInfo
import ch.njol.skript.expressions.base.PropertyExpression
import ch.njol.skript.expressions.base.SimplePropertyExpression
import ch.njol.skript.lang.*
import ch.njol.skript.lang.util.SimpleExpression
import ch.njol.skript.registrations.Classes
import ch.njol.util.Kleenean
import jdk.internal.dynalink.beans.StaticClass
import org.bukkit.Bukkit
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
        val name = name!!.getSingle(e) ?: return null
        val engine = ScriptEngineManager().getEngineByName(name) ?: return null

        // Bukkit stuff
        engine.put("Bukkit", Bukkit.getServer())
        engine.put("Server", Bukkit.getServer())
        engine.put("Scheduler", Bukkit.getScheduler())

        // Skript stuff
        engine.put("Skript", Skript.getInstance())
        engine.put("Effect", StaticClass.forClass(Effect::class.java))
        engine.put("SimpleExpression", StaticClass.forClass(SimpleExpression::class.java))
        engine.put("PropertyExpression", StaticClass.forClass(PropertyExpression::class.java))
        engine.put("SimplePropertyExpression", StaticClass.forClass(SimplePropertyExpression::class.java))
        engine.put("Expression", StaticClass.forClass(Expression::class.java))
        engine.put("Condition", StaticClass.forClass(Condition::class.java))
        engine.put("Effect", StaticClass.forClass(Effect::class.java))
        engine.put("Event", StaticClass.forClass(Event::class.java))
        engine.put("Classes", StaticClass.forClass(Classes::class.java))
        engine.put("ClassInfo", StaticClass.forClass(ClassInfo::class.java))
        engine.put("setRegistrationsEnabled", this::setRegistrationsEnabled)
        return arrayOf(engine)
    }

    private fun setRegistrationsEnabled(b: Boolean) {
        Skript::class.java.getDeclaredField("acceptRegistrations").setBoolean(null, b)
    }

    override fun isSingle(): Boolean {
        return true
    }

    override fun toString(e: Event?, debug: Boolean): String {
        return "idk new script engine expr"
    }

}