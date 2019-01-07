var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
    // user_id: String,
    // name: String,
    // phone: String,
    // friends: []
    user_id: {
        type: String,
        required: true,
        unique: true
    },
    name: {
        type: String,
        required: true,
        unique: false
    },
    phone: {
        type: String,
        required: true,
        unique: true
    },
    friends: [String]
})
module.exports = mongoose.model('user', userSchema);

