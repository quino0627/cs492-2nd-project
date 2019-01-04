var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var postSchema = new Schema({
    pictureUrl : String,
    uploader : String,
    date : String,
    contents : String,
    tag : [],
    post_id : String
})
module.exports = mongoose.model('post', postSchema);