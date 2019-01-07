let moment = require('moment');

module.exports = function (app, Post) {
    // CREATE NEW POST
    // app.post('/api/posts/create', async (req, res, next) => {
    //     try {
    //         User = require('../models/user');
    //         User.find({ user_id: req.body.uploader_id }, { name: 1 }, function (err, user) {
    //             if (err) return res.status(500).send({ error: err });
    //             if (user.length === 0) {
    //                 console.log(user + " " + req.params.uploader);
    //                 return res.status(404).json({ error: 'user not found' });
    //             }
    //             // console.log(user[0]);
    //             var current = moment().format("YYMMDDHHmmss");
    //             var post = new Post();
    //             const fs = require('fs');
    //             let buff = new Buffer(req.body.pictureUrl, 'base64');
    //             console.log(" 2");
    //             post.uploader_id = req.body.uploader_id;
    //             post.date = current;
    //             post.contents = req.body.contents;
    //             post.tag = req.body.tag;
    //             post.post_id = req.body.uploader_id + current;
    //             post.uploader_name = user[0].name;
    //             fs.writeFileSync('./storage/' + req.body.uploader_id + "++" + current + '.png', buff);
    //             post.pictureUrl = './storage/' + req.body.uploader_id + "++" + current + '.png';
    //             post.save(function (err) {
    //                 if (err) {
    //                     console.error(err);
    //                     res.json({ result: 0 });
    //                     return;
    //                 }
    //                 console.log("create new post");
    //                 res.json({ result: 1 });
    //             });
    //         })
    //     } catch (err) {
    //         next(err);
    //     }
    // });

    // CREATE POST WITH TENSOR
    app.post('/api/posts/create', async (req, res, next) => {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, create post")
        try {
            User = require('../models/user');
            User.find({ user_id: req.body.uploader_id }, { name: 1 }, function (err, user) {
                if (err) return res.status(500).send({ error: err });
                if (user.length === 0) {
                    console.log(user + " " + req.params.uploader);
                    return res.status(404).json({ error: 'user not found' });
                }
                var request = require('request');
                console.log("server request, tensor");
                request.post(
                    'http://143.248.36.99:80/tensor',
                    { json: req.body },
                    function (error, response, body) {
                        if (!error && response.statusCode == 200) {
                            // console.log(body)
                            console.log("server get" + response.body)
                            var current = moment().format("YYMMDDHHmmss");
                            var post = new Post();
                            const fs = require('fs');
                            let buff = new Buffer(req.body.pictureUrl, 'base64');
                            post.uploader_id = req.body.uploader_id;
                            post.date = current;
                            post.contents = req.body.contents;
                            post.tag = req.body.tag;
                            post.post_id = req.body.uploader_id + current;
                            post.uploader_name = user[0].name;
                            post.tag = response.body;
                            fs.writeFileSync('./storage/' + req.body.uploader_id + "++" + current + '.png', buff);
                            post.pictureUrl = './storage/' + req.body.uploader_id + "++" + current + '.png';
                            post.save(function (err) {
                                if (err) {
                                    console.error(err);
                                    res.json({ result: 0 });
                                    return;
                                }
                                console.log("create new post");
                                res.json({ result: 1 });
            
                            });
            
                        }
                    }
                );

            })


        } catch (err) {
            next(err);
        }

    });
    //GET ALL POSTS
    app.get('/api/posts/all', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get all posts")
        Post.find(function (err, posts) {
            if (err) return res.status(500).send({ error: 'database failure' });
            res.json(posts);
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
        })
    });
    //GET ONE's POSTS
    app.get('/api/posts/ones_posts/:uploader_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get one's posts")
        Post.find({ uploader_id: req.params.uploader_id }, { uploader_id:1, uploader_name:1, post_id: 1, pictureUrl: 1, date: 1, contents: 1, tag: 1, _id: 0 }, function (err, posts) {
            if (err) return res.status(500).json({ error: err });
            if (posts.length === 0) return res.status(404).json({ error: 'posts not found' });
            res.json(posts);
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
        })

    });
    //GET ONE's TIMELINE
    app.get('/api/posts/ones_timeline/:uploader_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get one's timeline")
        User = require('../models/user');
        User.find({ user_id: req.params.uploader_id }, { friends: 1, _id: 0 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length === 0) {
                console.log(users + " " + req.params.uploader);
                return res.status(404).json({ error: 'user not found' });
            }
            Post.find({ uploader_id: users[0].friends.concat(req.params.uploader_id) },
                { uploader_id: 1, uploader_name: 1, post_id: 1, pictureUrl: 1, date: 1, contents: 1, tag: 1, _id: 0 },
                function (err, posts) {
                    if (err) return res.status(500).json({ error: err });
                    if (posts.length === 0) {
                        // console.log(users[0].friends.concat(req.params.uploader));
                        return res.status(404).json({ error: 'posts not found' });
                    }
                    res.json(posts);
                    console.log(moment().format("YYMMDDHHmmss") + "    OK")
                })
                console.log(moment().format("YYMMDDHHmmss") + "    OK")
        })
    });

    //DELETE POST 
    app.delete('/api/posts/delete/:post_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, delete post")
        Post.remove({ post_id: req.params.post_id }, function (err, output) {
            if (err) return res.status(500).json({ error: "database failure" });
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "book not found" });
            res.json({ message: "book deleted" });
            */
            res.status(204).end();
        })
    });
    //UPDATE POST
    app.put('/api/posts/update/:post_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, update post")
        Post.find({ post_id: req.params.post_id }, { comments: 1, tag: 1, date: 1 }, function (err, post) {
            if (err) return res.status(500).json({ error: 'database failure' });
            if (post.length === 0) return res.status(404).json({ error: 'post not found' });

            if (req.body.contents) post[0].contents = req.body.contents;
            else console.log(req.body.contents);
            if (req.body.tag) post[0].tag = req.body.tag;
            if (req.body.date) post[0].date = moment().format("YYMMDDHHmmss");
            post[0].save(function (err) {
                if (err) res.status(500).json({ error: 'failed to update' });
                res.json({ message: 'post updated' });
                console.log("post update!");
            });

        });

    });
    //SEND TENSOR
    // app.post('/api/posts/tensor', function (req, res) {
    //     console.log(moment().format("YYMMDDHHmmss") +"    client request")
    //     var request = require('request');

    //     request.post(
    //         'http://143.248.36.99:80/tensor',
    //         { json: req.body },
    //         function (error, response, body) {
    //             if (!error && response.statusCode == 200) {
    //                 // console.log(body)
    //                 console.log(response.body)
    //                 res.send(response.body)
    //             }
    //         }
    //     );
    // })

}