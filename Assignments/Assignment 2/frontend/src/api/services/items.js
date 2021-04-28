import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allItems() {
    return HTTP.get(BASE_URL + "/items", { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  create(item) {
    return HTTP.post(BASE_URL + "/items", item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },
  deleteBook(item) {
    return HTTP.delete(BASE_URL + "/items/" + item.id, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  edit(item) {
    return HTTP.patch(BASE_URL + "/items/" + item.id, item, { headers: authHeader() }).then(
      (response) => {
        return response.data;
      }
    );
  },

    searchBooks(string){
      return HTTP.get(BASE_URL + "/items/" + string.string, { headers: authHeader() }).then(
          (response) => {
              return response.data;
          }
      );
    },

    generatePDF(){
        return HTTP.get(BASE_URL + "/items/export/PDF", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    },
    generateCSV(){
        return HTTP.get(BASE_URL + "/items/export/CSV", {headers: authHeader()}).then(
            (response) => {
                return response.data;
            }
        );
    }
};
