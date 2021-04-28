<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
          Sell Item
        </v-toolbar>
        <v-form>
          <v-text-field v-if="!isNew" v-model="item.id" label="ID" readonly />
          <v-text-field v-model="item.title" label="Title" readonly />
          <v-text-field v-model="item.author" label="Author" readonly />
          <v-text-field v-model="item.genre" label="Genre" readonly />
          <v-text-field v-model="item.quantity" label="Quantity" number readonly/>
          <v-text-field v-model="item.price" label="Price" readonly />
          <v-text-field v-model="sellNumber" label="Choose how many to sell." number />
        </v-form>

        <v-card-actions>

          <v-btn v-if="!isNew" v-on:click="sellBook">Sell Book</v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "SellDialog",
  props: {
    item: Object,
    opened: Boolean,
  },
  data(){
    return {
      sellNumber: null,
    }
  },
  methods: {


    sellBook(){
      this.item.quantity = this.item.quantity - this.sellNumber;
      api.items.edit({
        id: this.item.id,
        title: this.item.title,
        author: this.item.author,
        genre: this.item.genre,
        quantity: this.item.quantity,
        price: this.item.price,
      })
          .then(() => this.$emit("refresh"));
    }
  },
  computed: {
    isNew: function () {
      return !this.item.id;
    },
  },
};
</script>

<style scoped></style>
