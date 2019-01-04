var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
    user_id: String,
    name : String,
    phone: String,
    friends: [String]
})

module.exports = mongoose.model('user',userSchema);