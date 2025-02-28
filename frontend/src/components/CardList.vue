<script>
import axios from "axios";

export default {
  props: {
    gridMode: Boolean, // Controls grid layout
  },
  data() {
    return {
      items: [],
      selectedNote: null, // Stores the selected note
      loading: true,
      error: null,
    };
  },
  async created() {
    this.fetchNotes();
  },

  methods: {
    // Fetch all notes
    async fetchNotes() {
      this.loading = true;
      try {
        const response = await axios.get("http://localhost:8080/notes");
        this.items = response.data;
      } catch (err) {
        this.error = "Failed to fetch notes!";
      } finally {
        this.loading = false;
      }
    },

    // Select a note for editing
    selectNote(note) {
      this.selectedNote = { ...note }; // Copy note data
    },

    // Update a note
    async updateNote() {
      if (!this.selectedNote) return;

      try {
        await axios.patch(`http://localhost:8080/notes/${this.selectedNote.id}`, {
          title: this.selectedNote.title,
          content: this.selectedNote.content,
        });

        // Update local list
        const index = this.items.findIndex((n) => n.id === this.selectedNote.id);
        if (index !== -1) this.items[index] = { ...this.selectedNote };

        this.selectedNote = null; // Close detail view
      } catch (err) {
        alert("Failed to update note!");
      }
    },

    // Delete a note
    async deleteNote(noteId) {
      try {
        await axios.delete(`http://localhost:8080/notes/${noteId}`);
        this.items = this.items.filter((note) => note.id !== noteId);
        if (this.selectedNote && this.selectedNote.id === noteId) {
          this.selectedNote = null;
        }
      } catch (err) {
        alert("Failed to delete note!");
      }
    },
  },
};
</script>

<template>
  <div>
    <button v-if="!gridMode" @click="$emit('create-note')" class="btn">
      Create New Note
    </button>

    <div v-if="loading">Loading...</div>
    <div v-if="error">{{ error }}</div>

    <h5 v-if="!gridMode">PINNED</h5>
    <div :class="{ grid: gridMode, list: !gridMode }">
      <div
        v-for="item in items"
        :key="item.id"
        class="card"
        @click="selectNote(item)"
      >
        <h3 class="card-title">{{ item.title }}</h3>
        <p class="card-content">{{ item.content }}</p>
      </div>
    </div>

    <!-- Note Detail View -->
    <div v-if="selectedNote" class="overlay">
      <div class="detail-view">
        <h2>Edit Note</h2>
        <input v-model="selectedNote.title" class="title-input" />
        <textarea v-model="selectedNote.content" class="content-input"></textarea>

        <div class="buttons">
          <button @click="updateNote" class="btn save">Save</button>
          <button @click="deleteNote(selectedNote.id)" class="btn delete">Delete</button>
          <button @click="selectedNote = null" class="btn close">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Button */
.btn {
  padding: 8px 12px;
  background: rgba(18, 168, 232, 0.824);
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 5px;
  margin-bottom: 12px;
}

/* Grid Layout */
.grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(300px, 1fr)); 
  gap: 16px;
}

/* List Layout */
.list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* Notes Card */
.card {
  background: #fff;
  padding: 12px;
  border-radius: 6px;
  box-shadow: 0px 1px 4px rgba(0, 0, 0, 0.1);
  transition: 0.3s;
  cursor: pointer;
  position: relative;
}

.card:hover {
  transform: translateY(-3px);
}

/* Card Title */
.card-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 4px;
}

/* Card Content */
.card-content {
  font-size: 14px;
  color: #555;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

/* Overlay for detail view */
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
}

/* Note Detail View */
.detail-view {
  background: white;
  padding: 20px;
  width: 80%;
  max-width: 500px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* Input Fields */
.title-input,
.content-input {
  width: 100%;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.content-input {
  min-height: 150px;
  resize: none;
}

/* Buttons */
.buttons {
  display: flex;
  gap: 10px;
}

.btn.save {
  background: green;
}

.btn.delete {
  background: red;
}

.btn.close {
  background: gray;
}
</style>
