import authHeader, { BASE_URL, HTTP } from "../http";

export default {
  allUsers() {
    return HTTP.get(BASE_URL + "/users", {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  createUser(user) {
    return HTTP.post(BASE_URL + "/users", user, { headers: authHeader() }).then(
        (response) => {
          return response.data;
        }
    );
  },
  editUser(user) {
    return HTTP.patch(BASE_URL + "/users/" + user.id, user, {
      headers: authHeader(),
    }).then((response) => {
      return response.data;
    });
  },

  deleteUser(user) {
    return HTTP.delete(BASE_URL + "/users/" + user.id, {
      headers: authHeader(),
    }).then(
        (response) => {
          return response.data;
        },
        (error) => {
          alert(error.response.data.message);
        }
    );
  },
};
