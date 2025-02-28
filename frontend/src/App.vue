<script setup>
import { ref } from "vue";
import CardList from "./components/CardList.vue";
import NoteForm from "./components/NoteForm.vue";

const showForm = ref(false);

const openNoteForm = () => {
  showForm.value = true;
};

const closeNoteForm = () => {
  showForm.value = false;
};
</script>

<template>
  <div class="app-container">
    <!-- Sidebar with Note Creation Button -->
    <aside class="sidebar">
      <CardList @create-note="openNoteForm" hide-cards />
    </aside>

    <!-- Main Content Area -->
    <main class="content">
      <NoteForm v-if="showForm" @note-created="closeNoteForm" />
      <section v-else>
        <h2 class="title">Your Notes</h2>
        <CardList grid-mode class="full-width-grid" />
      </section>
    </main>
  </div>
</template>



<style scoped>
/* Full-screen Layout */
.app-container {
  display: flex;
  height: 100vh;
  background: #f1f3f4;
  width: 100%;
}

/* Sidebar */
.sidebar {
  width: 250px;
  background: #fff;
  padding: 20px;
  border-right: 1px solid #ddd;
}

/* Main Content */
.content {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

/* Title Styling */
.title {
  font-size: 24px;
  margin-bottom: 20px;
}

/* Grid Layout for Full Width */
.full-width-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); /* Ensures 2 columns */
  gap: 20px;
  width: 100%; /* Ensures it spans the full content width */
  max-width: 1200px; /* Prevents excessive stretching */
}
</style>
