const API_URL =
  process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080/api";

export type Task = {
  id: number;
  title: string;
  description: string | null;
  completed: boolean;
  createdAt: string;
};

export type CreateTaskRequest = {
  title: string;
  description: string;
};

async function request<T>(
  endpoint: string,
  options: RequestInit = {}
): Promise<T> {
  const response = await fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...options.headers,
    },
  });

  if (!response.ok) {
    const body = await response.text();
    throw new Error(body || `Erreur HTTP ${response.status}`);
  }

  if (response.status === 204) {
    return undefined as T;
  }

  return response.json() as Promise<T>;
}

export const tasksApi = {
  getAll: () => request<Task[]>("/tasks"),

  getById: (id: number) => request<Task>(`/tasks/${id}`),

  create: (data: CreateTaskRequest) =>
    request<Task>("/tasks", {
      method: "POST",
      body: JSON.stringify(data),
    }),

  update: (id: number, data: CreateTaskRequest) =>
    request<Task>(`/tasks/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    }),

  toggleCompletion: (id: number) =>
    request<Task>(`/tasks/${id}/completion`, {
      method: "PATCH",
    }),

  delete: (id: number) =>
    request<void>(`/tasks/${id}`, {
      method: "DELETE",
    }),
};
