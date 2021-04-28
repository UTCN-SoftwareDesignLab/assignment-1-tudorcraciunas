<template>
  <v-card color="grey lighten-4" flat tile>
    <v-toolbar>
      <v-app-bar-nav-icon> </v-app-bar-nav-icon>

      <v-btn v-if="isAdmin" icon @click="goToUsers">
        <v-icon>mdi-account</v-icon>
      </v-btn>

      <v-btn v-if="isAdmin" icon @click="goToItems">
        <v-icon>mdi-book-open-page-variant</v-icon>
      </v-btn>

      <v-toolbar-title>BookStore</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon @click="logout">
        <v-icon>logout</v-icon>
      </v-btn>
    </v-toolbar>
  </v-card>
</template>

<script>
//import { mdiBookOpenPageVariant } from "@mdi/js";
import router from "../router";

export default {
  name: "TopBar",
  computed: {
    isAdmin(){
      return this.$route.path === "/users" || this.$route.path === "/items";
    }
  },
  methods: {
    logout() {
      this.$store.dispatch("auth/logout");
      router.push("/");
    },
    goToUsers(){
        router.push("/users");
    },
    goToItems(){
      router.push("/items");
    },
  },
};
</script>

<style scoped></style>
