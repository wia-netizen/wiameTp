<template>
  <div class="container">
    <h2>Enregistrer une participation</h2>

    <!-- Sélection de la personne -->
    <label>Personne</label>
    <select v-model="selectedPerson">
      <option v-for="person in persons" :key="person.matricule" :value="person.matricule">
        {{ person.prenom }} {{ person.nom }}
      </option>
    </select>

    <!-- Sélection du projet -->
    <label>Projet</label>
    <select v-model="selectedProject">
      <option v-for="project in projects" :key="project.id" :value="project.id">
        {{ project.nom }}
      </option>
    </select>

    <!-- Rôle -->
    <label>Rôle</label>
    <input type="text" v-model="role" placeholder="Développeur">

    <!-- Pourcentage -->
    <label>Pourcentage</label>
    <input type="range" v-model="percentage" min="10" max="100" step="10">
    <span>{{ percentage }}%</span>

    <!-- Bouton Enregistrer -->
    <button @click="submitParticipation">Enregistrer</button>

    <!-- Affichage des erreurs -->
    <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      persons: [],
      projects: [],
      selectedPerson: null,
      selectedProject: null,
      role: '',
      percentage: 10,
      errorMessage: ''
    };
  },
  mounted() {
    this.fetchPersons();
    this.fetchProjects();
  },
  methods: {
    async fetchPersons() {
      try {
        const response = await axios.get('/api/gestion/personnes');
        this.persons = response.data;
      } catch (error) {
        console.error("Erreur lors du chargement des personnes", error);
      }
    },
    async fetchProjects() {
      try {
        const response = await axios.get('/api/gestion/projets');
        this.projects = response.data.filter(p => !p.fin); // Filtrer les projets non terminés
      } catch (error) {
        console.error("Erreur lors du chargement des projets", error);
      }
    },
    async submitParticipation() {
      try {
        await axios.post('/api/gestion/participation', {
          matricule: this.selectedPerson,
          codeProjet: this.selectedProject,
          role: this.role,
          pourcentage: this.percentage / 100 // Stocké sous forme décimale
        });
        alert("Participation enregistrée avec succès !");
        this.errorMessage = '';
      } catch (error) {
        this.errorMessage = error.response?.data?.message || "Une erreur est survenue.";
      }
    }
  }
};
</script>

<style>
.container {
  width: 300px;
  margin: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
label {
  display: block;
  margin-top: 10px;
}
input, select {
  width: 100%;
  margin-top: 5px;
  padding: 5px;
}
button {
  background-color: blue;
  color: white;
  padding: 10px;
  margin-top: 15px;
  width: 100%;
  border: none;
  cursor: pointer;
}
.error {
  color: red;
}
</style>
