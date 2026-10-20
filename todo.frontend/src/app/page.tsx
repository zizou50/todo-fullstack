"use client";

import { useCallback, useEffect, useState } from "react";
import { tasksApi, type Task } from "@/lib/tasks-api";

export default function Home() {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");

  const loadTasks = useCallback(async () => {
    setLoading(true);
    setError("");

    try {
      const data = await tasksApi.getAll();
      setTasks(data);
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Impossible de charger les tâches."
      );
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    void loadTasks();
  }, [loadTasks]);

  async function addTask(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    if (!title.trim()) return;

    setSaving(true);
    setError("");

    try {
      await tasksApi.create({
        title: title.trim(),
        description: description.trim(),
      });

      setTitle("");
      setDescription("");
      await loadTasks();
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Impossible de créer la tâche."
      );
    } finally {
      setSaving(false);
    }
  }

  async function toggleTask(id: number) {
    setError("");

    try {
      await tasksApi.toggleCompletion(id);
      await loadTasks();
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Impossible de modifier la tâche."
      );
    }
  }

  async function deleteTask(id: number) {
    setError("");

    try {
      await tasksApi.delete(id);
      await loadTasks();
    } catch (err) {
      setError(
        err instanceof Error
          ? err.message
          : "Impossible de supprimer la tâche."
      );
    }
  }

  return (
    <main className="min-h-screen bg-slate-100 px-4 py-12 text-slate-900">
      <div className="mx-auto max-w-2xl">
        <header className="mb-8">
          <p className="mb-2 text-sm font-semibold uppercase tracking-widest text-blue-600">
            My workspace
          </p>
          <h1 className="text-4xl font-bold tracking-tight">Todo List</h1>
          <p className="mt-3 text-slate-600">
            Organise tes tâches et suis ta progression.
          </p>
        </header>

        <section className="mb-6 rounded-2xl bg-white p-6 shadow-sm">
          <h2 className="mb-4 text-xl font-semibold">Ajouter une tâche</h2>

          <form onSubmit={addTask} className="space-y-4">
            <div>
              <label htmlFor="title" className="mb-1 block text-sm font-medium">
                Titre
              </label>
              <input
                id="title"
                value={title}
                onChange={(event) => setTitle(event.target.value)}
                placeholder="Ex. : Apprendre Next.js"
                maxLength={150}
                required
                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
              />
            </div>

            <div>
              <label htmlFor="description" className="mb-1 block text-sm font-medium">
                Description
              </label>
              <textarea
                id="description"
                value={description}
                onChange={(event) => setDescription(event.target.value)}
                placeholder="Quelques détails sur cette tâche..."
                maxLength={1000}
                rows={3}
                className="w-full rounded-lg border border-slate-300 px-4 py-3 outline-none focus:border-blue-500 focus:ring-2 focus:ring-blue-100"
              />
            </div>

            <button
              type="submit"
              disabled={saving}
              className="w-full rounded-lg bg-blue-600 px-4 py-3 font-semibold text-white transition hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
              {saving ? "Enregistrement..." : "+ Ajouter la tâche"}
            </button>
          </form>
        </section>

        {error && (
          <div
            role="alert"
            className="mb-6 break-words rounded-lg border border-red-200 bg-red-50 p-4 text-sm text-red-700"
          >
            Une erreur est survenue : {error}
            <button
              type="button"
              onClick={() => void loadTasks()}
              className="ml-2 font-semibold underline"
            >
              Réessayer
            </button>
          </div>
        )}

        <section className="rounded-2xl bg-white p-6 shadow-sm">
          <div className="mb-5 flex items-center justify-between">
            <h2 className="text-xl font-semibold">Mes tâches</h2>
            <span className="rounded-full bg-blue-50 px-3 py-1 text-sm font-medium text-blue-700">
              {tasks.filter((task) => !task.completed).length} restante(s)
            </span>
          </div>

          {loading ? (
            <p className="py-8 text-center text-slate-500">
              Chargement des tâches...
            </p>
          ) : tasks.length === 0 ? (
            <p className="rounded-lg border border-dashed border-slate-300 p-8 text-center text-slate-500">
              Aucune tâche pour le moment. Ajoute ta première tâche !
            </p>
          ) : (
            <ul className="space-y-3">
              {tasks.map((task) => (
                <li
                  key={task.id}
                  className="flex items-start gap-3 rounded-xl border border-slate-200 p-4"
                >
                  <input
                    type="checkbox"
                    checked={task.completed}
                    onChange={() => void toggleTask(task.id)}
                    aria-label={`Marquer ${task.title} comme terminée`}
                    className="mt-1 h-4 w-4 accent-blue-600"
                  />

                  <div className="min-w-0 flex-1">
                    <p
                      className={`break-words font-medium ${
                        task.completed
                          ? "text-slate-400 line-through"
                          : "text-slate-900"
                      }`}
                    >
                      {task.title}
                    </p>
                    {task.description && (
                      <p className="mt-1 break-words text-sm text-slate-500">
                        {task.description}
                      </p>
                    )}
                    <span className="mt-2 inline-block text-xs text-slate-400">
                      {task.completed ? "Terminée" : "En cours"}
                    </span>
                  </div>

                  <button
                    type="button"
                    onClick={() => void deleteTask(task.id)}
                    className="shrink-0 rounded-md px-3 py-2 text-sm font-medium text-red-600 transition hover:bg-red-50"
                    aria-label={`Supprimer ${task.title}`}
                  >
                    Supprimer
                  </button>
                </li>
              ))}
            </ul>
          )}
        </section>

        <p className="mt-6 text-center text-sm text-slate-500">
          Todo List · Next.js + Spring Boot
        </p>
      </div>
    </main>
  );
}
