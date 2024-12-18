package com.mikel.scroll_infinito

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikel.scroll_infinito.TaskApplication.Companion.prefs

/**
 * MainActivity es la actividad principal de la aplicación que permite al usuario gestionar una lista de tareas.
 * Proporciona funcionalidades para agregar, eliminar y mostrar tareas, así como reproducir un sonido al eliminar.
 */
class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask: Button // Botón para añadir una nueva tarea
    lateinit var etTask: EditText // Campo de texto para ingresar la tarea
    lateinit var rvTasks: RecyclerView // RecyclerView para mostrar la lista de tareas

    lateinit var adapter: TaskAdapter // Adaptador para gestionar la lista de tareas
    lateinit var mediaPlayer: MediaPlayer // Declarar el MediaPlayer para reproducir sonidos

    var tasks = mutableListOf<String>() // Lista mutable que contiene las tareas

    /**
     * Método de creación de la actividad.
     * Inicializa la interfaz de usuario y el MediaPlayer.
     *
     * @param savedInstanceState Estado guardado de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()

        // Inicializar MediaPlayer con el archivo de sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.delete_sound)
    }

    /**
     * Inicializa la interfaz de usuario, incluyendo vistas, oyentes y el RecyclerView.
     */
    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    /**
     * Inicializa el RecyclerView con la lista de tareas.
     * Configura el adaptador y el administrador de diseño.
     */
    private fun initRecyclerView() {
        tasks = prefs.getTasks() // Obtener tareas guardadas
        rvTasks.layoutManager = LinearLayoutManager(this) // Configurar el diseño lineal
        adapter = TaskAdapter(tasks) { deleteTask(it) } // Crear el adaptador con la lista de tareas
        rvTasks.adapter = adapter // Asignar el adaptador al RecyclerView
    }

    /**
     * Elimina una tarea de la lista en la posición especificada.
     * Reproduce un sonido de eliminación después de eliminar la tarea.
     *
     * @param position Posición de la tarea a eliminar.
     */
    private fun deleteTask(position: Int) {
        tasks.removeAt(position) // Eliminar la tarea de la lista
        adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
        prefs.saveTasks(tasks) // Guardar la lista actualizada

        // Reproducir sonido de eliminación
        mediaPlayer.start()
    }

    /**
     * Inicializa los oyentes de eventos para las vistas.
     * En particular, configura el oyente del botón para añadir tareas.
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener { addTask() }
    }

    /**
     * Agrega una nueva tarea a la lista si el campo de texto no está vacío.
     * Actualiza el RecyclerView y guarda la lista de tareas.
     */
    private fun addTask() {
        val taskToAdd: String = etTask.text.toString() // Obtener el texto de la tarea
        if (taskToAdd.isNotEmpty()) { // Verificar que el campo no esté vacío
            tasks.add(taskToAdd) // Añadir la nueva tarea a la lista
            prefs.saveTasks(tasks) // Guardar la lista actualizada
            adapter.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
            etTask.setText("") // Limpiar el campo de texto
        }
    }

    /**
     * Inicializa las vistas y enlaza los elementos de la interfaz de usuario.
     */
    private fun initView() {
        btnAddTask = findViewById(R.id.btn_aniadirTarea) // Enlazar el botón de añadir tarea
        etTask = findViewById(R.id.txt_tarea) // Enlazar el campo de texto
        rvTasks = findViewById(R.id.View_listaTarea) // Enlazar el RecyclerView
    }

    /**
     * Se llama cuando la actividad se destruye.
     * Libera los recursos utilizados por el MediaPlayer.
     */
    override fun onDestroy() {
        super.onDestroy()
        // Liberar el MediaPlayer cuando la actividad se destruya
        mediaPlayer.release()
    }
}
