package com.mikel.scroll_infinito

import android.app.Application

/**
 * Clase personalizada de aplicación para inicializar preferencias compartidas.
 *
 * Esta clase extiende Application y se utiliza para almacenar una instancia global de Preferences
 * que se puede acceder desde cualquier parte de la aplicación.
 */
class TaskApplication : Application() {

    companion object {
        lateinit var prefs: Preferences // Instancia global de Preferences
    }

    /**
     * Método llamado al crear la aplicación.
     * Inicializa la instancia de Preferences utilizando el contexto de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        prefs = Preferences(baseContext) // Inicializar Preferences con el contexto de la aplicación
    }
}
