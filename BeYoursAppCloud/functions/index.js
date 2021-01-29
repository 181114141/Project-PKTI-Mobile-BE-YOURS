const functions = require("firebase-functions");

// The Firebase Admin SDK to access Firestore.
const admin = require("firebase-admin");
admin.initializeApp();

// Create and Deploy Your First Cloud Functions
// https://firebase.google.com/docs/functions/write-firebase-functions

exports.helloWorld = functions.https.onRequest((request, response) => {
    functions.logger.info("Hello logs!", { structuredData: true });
    response.send("Hello from Firebase!");
});


exports.addUserToFirestore = functions.auth.user().onCreate((user) => {
    // Dijalankan setiap user baru dibuat
    const usersRef = admin.firestore().collection("users");
    return usersRef.doc(user.uid).set({
        displayName: user.displayName,
        toyName: "Nama mainan yang dibarter",
        description: "Deskripsi mainan yang akan dibarter",
        email: user.email,
        idName: "Nama ID",
        phone: "08xx-xxxx-xxxx",
        address: "Alamat rumah",
        imageUrl: ""
    });
});