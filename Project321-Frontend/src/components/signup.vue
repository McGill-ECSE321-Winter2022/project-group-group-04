<template>
<!-- This page provides all the textfields for a customer to create an account -->
<div id="signup">
    <h2 style="text-align:center">Create your account below</h2>
    <div class="item">
        <table>
            <tr>
                <td class="table_cell">
                    <table>
                        <tr>
                            <td class="table_cell">
                                <p style="text-align=left">Personal information</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_cell">
                                <hr>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_cell">
                                <table>
                                    <tr>
                                        <table>
                                            <tr>
                                                <td class="table_cell">
                                                    <p style="text-align=left">Firstname</p>
                                                </td>
                                                <td class="table_cell">
                                                    <p style="text-align=left">Lastname</p>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td class="table_cell">
                                                    <input type="text" v-model="newFirstname" class="small_input" placeholder="Firstname">
                                                </td>
                                                <td class="table_cell">
                                                    <input type="text" v-model="newLastname" class="small_input" placeholder="Lastname">
                                                </td>
                                            </tr>
                                        </table>
                                    </tr>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Email</p>
                                            <input type="text" v-model="newEmail" class="big_input" placeholder="Email">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Phone Number</p>
                                            <input type="tel" v-model="newPhone" class="big_input" placeholder="Phone Number">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Password</p>
                                            <input type="password" v-model="newPassword" class="big_input" placeholder="Password">
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
                <td class="separator_cell">
                </td>
                <td class="table_right_column">
                    <table>
                        <tr>
                            <td class="table_cell">
                                <p style="text-align=left">Address</p>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_cell">
                                <hr>
                            </td>
                        </tr>
                        <tr>
                            <td class="table_cell">
                                <table>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Unit Number/Address Number</p>
                                            <input type="tel" v-model="newUnit" class="big_input" placeholder="Unit Number/Address Number">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Street</p>
                                            <input type="tel" v-model="newStreet" class="big_input" placeholder="Street">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Town</p>
                                            <input type="tel" v-model="newTown" class="big_input" placeholder="Town">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="table_cell">
                                            <p style="text-align=left">Postal Code</p>
                                            <input type="tel" v-model="newPostalCode" class="big_input" placeholder="Postal Code">
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <span v-if="newPassword.length > 0 && newPassword.length < 7" class="password_error_message">Password must contain more than 6 characters</span>
    </div>
    <div>
        <button class="signup_button" v-bind:disabled="signupEnabled()" @click="createCustomerAccount()">Sign Up</button>
    </div>
    <div>
        <span v-if="error.length > 0" class="error_message">Error: {{error}} </span>
    </div>
</div>
</template>

<style>
#signup {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
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
    display: block;
    text-align: center;
}

.password_error_message {
    color: red;
    font-size: 14px;
    display: block;
    text-align: left;
}

.signup_button {
    margin-bottom: 2rem;
    margin-top: 2rem;
    width: 240px;
}

.item {
    display: inline-block;
}
</style>

<script src="./scripts/signup.js"></script>
