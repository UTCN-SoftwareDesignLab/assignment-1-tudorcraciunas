<template>
  <v-card>
    <v-card-title>
      Items
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-btn @click="addItem">Add Item</v-btn>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="items"
      :search="search"
      @click:row="editItem"
    ></v-data-table>
    <ItemDialog
      :opened="dialogVisible"
      :item="selectedItem"
      @refresh="refreshList"
    ></ItemDialog>
    <v-btn @click="generatePDF">PDF Report</v-btn>
    <v-btn @click="generateCSV">CSV Report</v-btn>
    <v-spacer></v-spacer>

    <v-text-field v-model="searchBook" label="Search by Title, Author or Genre" />
    <v-btn @click="searchBooks">Search</v-btn>


  </v-card>
</template>

<script>
import api from "../api";
import ItemDialog from "../components/ItemDialog";

export default {
  name: "ItemList",
  components: { ItemDialog },
  data() {
    return {
      items: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedItem: {},
      searchBook: "",
    };
  },
  methods: {
    editItem(item) {
      this.selectedItem = item;
      this.dialogVisible = true;
    },
    addItem() {
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedItem = {};
      this.items = await api.items.allItems();
    },
    generatePDF(){
      api.items.generatePDF();
    },
    generateCSV(){
      api.items.generateCSV();
    },
    async searchBooks(){

      this.items = await api.items.searchBooks({
        string : this.searchBook})
    }
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
