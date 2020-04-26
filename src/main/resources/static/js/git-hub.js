$('#github-button').on('click', function() {
    // Initialize with your OAuth.io app public key
    OAuth.initialize('YOUR OAUTH.IO PUBLIC KEY');
    // Use popup to prompt user for their OAuth provider credentials
    OAuth.popup('github').then(github => {
        // If login is successful,
        // retrieve user data from oauth provider
        console.log(github.me());
    });
})