<template>
  <div id="edit_store_info_page">
    <button class="logout_button" @click="logout()">Logout</button>
    <h2 style="text-align: center">This is the edit store information page</h2>
    <h3 style="text-align: center; margin-top: 50px; margin-left: 50px">
      Opening Hour
    </h3>
    <table class="table_style">
      <tr>
        <td>{{ store.openingHour }}</td>
      </tr>
    </table>
    <span v-if="error.length > 0" class="error_message"
      >Error: {{ error }}
    </span>
    <div id ="createStore">
    <table class="table_style2">
      <tr>
        <td class="table_cell">
          <table>
            <tr>
              <td class="table_cell">
                <p style="text-align=left">Information</p>
              </td>
            </tr>
            <tr>
              <td class="table_cell">
                <hr />
              </td>
            </tr>
            <tr>
              <td class="table_cell">
                <table>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Email</p>
                      <input
                        type="text"
                        v-model="newEmail"
                        class="big_input"
                        placeholder="Email"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Phone Number</p>
                      <input
                        type="tel"
                        v-model="newTelephone"
                        class="big_input"
                        placeholder="Phone Number"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Opening Hours</p>
                      <input
                        type="tel"
                        v-model="newOpeningHour"
                        class="small_input"
                        placeholder="HH:mm:ss"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Closing Hours</p>
                      <input
                        type="tel"
                        v-model="newClosingHour"
                        class="small_input"
                        placeholder="HH:mm:ss"
                      />
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
        <td class="separator_cell"></td>
        <td class="table_right_column">
          <table>
            <tr>
              <td class="table_cell">
                <p style="text-align=left">Address</p>
              </td>
            </tr>
            <tr>
              <td class="table_cell">
                <hr />
              </td>
            </tr>
            <tr>
              <td class="table_cell">
                <table>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Unit Number/Address Number</p>
                      <input
                        type="tel"
                        v-model="newUnit"
                        class="big_input"
                        placeholder="Unit Number/Address Number"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Street</p>
                      <input
                        type="tel"
                        v-model="newStreet"
                        class="big_input"
                        placeholder="Street"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Town</p>
                      <input
                        type="tel"
                        v-model="newTown"
                        class="big_input"
                        placeholder="Town"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Postal Code</p>
                      <input
                        type="tel"
                        v-model="newPostalCode"
                        class="big_input"
                        placeholder="Postal Code"
                      />
                    </td>
                  </tr>
                  <tr>
                    <td class="table_cell">
                      <p style="text-align=left">Out of Town Fee</p>
                      <input
                        type="tel"
                        v-model="newOutOfTownFee"
                        class="big_input"
                        placeholder="Out of Town Fee"
                      />
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <table> 
        <button class="create_store_button" @click="createStore()">Create Store</button>
      </table>
    </table>
    </div>
  </div>
</template>

<style>
#edit_store_info_page {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  color: #2c3e50;
  background: white;
}
.logout_button {
  margin-top: 5px;
  margin-right: 5px;
  width: 70px;
  top: 0;
  right: 0;
  position: absolute;
}
.table_style {
  margin-left: auto;
  margin-right: auto;
  border-collapse: collapse;
  border-spacing: 0;
}
.table_style2 {
  margin-left: auto;
  margin-right: auto;
  border-collapse: collapse;
  border-spacing: 0;
}
.table_cell {
  width: 300px;
  text-align: left;
}
.separator_cell {
  width: 40px;
}
.small_input {
  text-align: left;
  width: 150px;
}
.big_input {
  text-align: left;
  width: 300px;
}
.error_message {
  color: red;
  text-align: center;
}
</style>

<script src="./scripts/edit_store_info_page.js"></script>