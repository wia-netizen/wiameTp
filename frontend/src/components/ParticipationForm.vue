<script setup>
import { ref } from 'vue';

const participation = ref({
  personne: '',
  projet: '',
  role: '',
  pourcentage: 10
});

// Options des personnes et projets
import { watchEffect } from 'vue';

const personnes = ref([
  { id: 1, nom: 'John Doe' },
  { id: 2, nom: 'Jane Smith' }
]);

const projets = ref([
  { id: 1, nom: 'Projet 1 (en cours)' },
  { id: 2, nom: 'Projet 2 (terminé)' },
  { id: 3, nom: 'Projet 3 (en cours)' }
]);

watchEffect(() => {
  console.log('Personnes disponibles:', personnes.value);
  console.log('Projets disponibles:', projets.value);
});

const submitForm = () => {
  console.log('Participation soumise:', participation.value);
};
</script>

<template>
  <div class="form-container">
    <h2>Enregistrer une participation</h2>

    <form @submit.prevent="submitForm">
      <label>Personne</label>
      <select v-model="participation.personne">
        <option v-for="personne in personnes" :key="personne.id" :value="personne.nom">
          {{ personne.nom }}
        </option>
      </select>

      <label>Projet</label>
      <select v-model="participation.projet">
        <option v-for="projet in projets" :key="projet.id" :value="projet.nom">
          {{ projet.nom }}
        </option>
      </select>

      <label>Rôle</label>
      <input v-model="participation.role" type="text" placeholder="Ex: Développeur" required />

      <label>Pourcentage</label>
      <input v-model="participation.pourcentage" type="range" min="0" max="100" step="5" />
      <p>{{ participation.pourcentage }}%</p>

      <button type="submit">Enregistrer</button>
    </form>
  </div>
</template>

<style scoped>
.form-container {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  max-width: 350px;
  margin: auto;
}

h2 {
  font-size: 20px;
  margin-bottom: 15px;
}

form {
  display: flex;
  flex-direction: column;
}

label {
  font-weight: bold;
  margin-top: 10px;
}

input, select {
  padding: 8px;
  margin-top: 5px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

input[type="range"] {
  margin-top: 10px;
}

button {
  margin-top: 15px;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
