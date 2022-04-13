<template>
<div id="login">
    <h2 style="text-align:center">Welcome to the Technologically Superior Grocery Store!</h2>
    <p style="text-align:center"> Enter your credentials bellow to login </p>

    <!-- This section will prompt the user to log in and automatically send all user to their corresponding page once password is varified -->
    <table class="login_table">
        <tr>
            <td>
                <p style="text-align:left"> Email Address </p>
            </td>
        </tr>
        <tr>
            <td> <input type="text" class="input_text" v-model="newUsername" placeholder="Email Address"> </td>
        </tr>
        <tr>
            <td>
                <p style="text-align:left"> Password </p>
            </td>
        </tr>
        <tr>
            <td> <input type="password" class="input_text" v-model="newPassword" placeholder="Password"> </td>
        </tr>
        <tr>
            <td> <button class="login_button" v-bind:disabled="!newUsername || !newPassword" @click="login(newUsername, newPassword)">Login</button> </td>
        </tr>
    </table>
    <p style="text-align:center">Don't have an account yet? <a href="#/signup">Sign up now!</a></p>
    <span v-if="error.length > 0" class="error_message">Error: {{error}} </span>
</div>
</template>

<style>
#login {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    color: #2c3e50;
    background: white;
}

.login_button {
    margin-bottom: 2rem;
    margin-top: 2rem;
    width: 240px;
}

.input_text {
    width: 240px;
    margin-bottom: none;
    margin-top: none;
}

.login_table {
    margin-left: auto;
    margin-right: auto;
    border-collapse: collapse;
    border-spacing: 0;
}

.error_message {
    color: red;
    text-align: center;
}
</style>

<script src="./scripts/login.js"></script>
