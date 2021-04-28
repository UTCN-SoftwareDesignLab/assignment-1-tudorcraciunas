<template>
  <v-card>
    <v-card-title>
      Users
      <v-spacer></v-spacer>
      <v-text-field
          v-model="search"
          append-icon="mdi-magnify"
          label="Search"
          single-line
          hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
        :headers="UserList"
        :items="usersItem"
        :search="search"
        @click:row="editUser"
    ></v-data-table>
    <UserDialog
        :opened="userDialogVisibility"
        :item="userSelected"
        @refresh="refreshList"
    ></UserDialog>
    <v-btn @click="addUser">Add User</v-btn>
  </v-card>
</template>

<script>
import api from "../api";
import UserDialog from "@/components/UserDialog";
export default {
  name: "UserList",
  components: { UserDialog },
  data() {
    return {
      usersItem: [],
      search: "",
      UserList: [
        { text: "ID", align: "start", sortable: false, value: "id"},
        { text: "Username", value: "name" },
        { text: "Email", value: "email" },
        { text: "Role", value: "roles"},
      ],
      userDialogVisibility: false,
      userSelected: {},
    };
  },
  methods: {
    addUser() {
      this.userDialogVisibility = true;
    },
    editUser(user) {
      this.userSelected = user;
      this.userDialogVisibility = true;
    },
    async refreshList() {
      this.usersItem = await api.users.allUsers();
      this.userDialogVisibility = false;
      this.userSelected = {};
    },

  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>