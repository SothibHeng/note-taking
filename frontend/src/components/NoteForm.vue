<script setup>
import { ref, defineEmits } from "vue";
import axios from "axios";

const emits = defineEmits(["created", "close"]);
const note = ref({ title: "", content: "" });

const createNote = async () => {
  if (!note.value.title && !note.value.content) return;

  try {
    await axios.post("http://localhost:8080/notes", note.value);
    emits("created");
    note.value = { title: "", content: "" };
  } catch (error) {
    console.error("Error creating note:", error);
  }
};

const closeForm = () => {
  emits("close");
};
</script>

<template>
  <div class="note-form">
    <input v-model="note.title" placeholder="Title" class="title-input" />
    <textarea v-model="note.content" placeholder="Take a note..." class="content-input"></textarea>
    <div class="button-section">
      <button @click="createNote" class="btn btn-primary">Save</button>
      <button @click="closeForm" class="btn btn-secondary">Close</button>
    </div>
  </div>
</template>

<style scoped>

.button-section {
  display: flex;
  gap: 10px;
}

.note-form {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background: white;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.title-input {
  width: 80%;
  max-width: 800px;
  font-size: 20px;
  font-weight: bold;
  padding: 10px;
  border: none;
  border-bottom: 2px solid #dddddd;
  outline: none;
}

.content-input {
  width: 80%;
  max-width: 800px;
  height: 300px;
  font-size: 16px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  outline: none;
  resize: none;
}

.btn {
  padding: 10px 15px;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  font-size: 16px;
}

.btn-primary {
  background: rgb(18, 137, 242);
  color: white;
}

.btn-secondary {
  background: #ddd;
  color: black;
}
</style>
