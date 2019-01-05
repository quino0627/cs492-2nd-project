let moment = require('moment');

module.exports = function (app, Post) {
    // CREATE NEW POST

    // app.post('/api/posts/create', function (req, res) {
    //     var current = moment().format("YYMMDDHHmmss");
    //     var post = new Post();
    //     const fs = require('fs');
    //     let buff = new Buffer(req.body.pictureUrl, 'base64');
    //     fs.writeFileSync('./storage/' + req.body.uploader + "++" + current + '.png', buff);
    //     post.pictureUrl = './storage/' + req.body.uploader + "++" + current + '.png';

    //     post.uploader = req.body.uploader;
    //     post.date = current;
    //     post.contents = req.body.contents;
    //     post.tag = req.body.tag;
    //     post.post_id = req.body.uploader + current;

    //     post.save(function (err) {
    //         if (err) {
    //             console.error(err);
    //             res.json({ result: 0 });
    //             return;
    //         }
    //         console.log("create new post");
    //         res.json({ result: 1 });

    //     });

    // });
    app.post('/api/posts/create', async (req, res, next) => {
        try {
            var current = moment().format("YYMMDDHHmmss");
            var post = new Post();
            const fs = require('fs');
            let buff = new Buffer(req.body.pictureUrl, 'base64');


            post.uploader = req.body.uploader;
            post.date = current;
            post.contents = req.body.contents;
            post.tag = req.body.tag;
            post.post_id = req.body.uploader + current;
            await fs.writeFileSync('./storage/' + req.body.uploader + "++" + current + '.png', buff);
            await (post.pictureUrl = './storage/' + req.body.uploader + "++" + current + '.png');
            post.save(function (err) {
                if (err) {
                    console.error(err);
                    res.json({ result: 0 });
                    return;
                }
                console.log("create new post");
                res.json({ result: 1 });

            });

        } catch (err) {
            next(err);
        }

    });
    //GET ALL POSTS
    app.get('/api/posts/all', function (req, res) {
        Post.find(function (err, posts) {
            if (err) return res.status(500).send({ error: 'database failure' });
            res.json(posts);

            // console.log(posts);
            console.log("client request getAllposts");
        })
    });
    //GET ONE's POSTS
    app.get('/api/posts/ones_posts/:uploader', function (req, res) {
        Post.find({ uploader: req.params.uploader }, { post_id: 1, pictureUrl: 1, date: 1, contents: 1, tag: 1, _id: 0 }, function (err, posts) {
            if (err) return res.status(500).json({ error: err });
            if (posts.length === 0) return res.status(404).json({ error: 'posts not found' });
            res.json(posts);
            console.log(posts);
        })
       
    });
    //GET ONE's TIMELINE
    app.get('/api/posts/ones_timeline/:uploader', function (req, res) {
        User = require('../models/user');
        User.find({ user_id: req.params.uploader }, { friends: 1, _id: 0 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length === 0) {
                console.log(users + " " + req.params.uploader);
                return res.status(404).json({ error: 'user not found' });
            }
            Post.find({uploader: users[0].friends.concat(req.params.uploader)},{ post_id: 1, pictureUrl: 1, date: 1, contents: 1, tag: 1, _id: 0 }, function (err, posts){
                if (err) return res.status(500).json({ error: err });
                if (posts.length === 0) {
                    console.log(users[0].friends.concat(req.params.uploader));
                    return res.status(404).json({ error: 'posts not found' });
                }
                res.json(posts);
                console.log(posts);
                console.log(users[0].friends.concat(req.params.uploader));
            })
        })
    });

    //DELETE POST 
    app.delete('/api/posts/delete/:post_id', function (req, res) {
        Post.remove({ post_id: req.params.post_id }, function (err, output) {
            if (err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "book not found" });
            res.json({ message: "book deleted" });
            */
            res.status(204).end();
        })
    });
    //UPDATE POST
    app.put('/api/posts/update/:post_id', function (req, res) {
        Post.find({ post_id: req.params.post_id }, { comments: 1, tag: 1, date: 1 }, function (err, post) {
            if (err) return res.status(500).json({ error: 'database failure' });
            if (post.length === 0) return res.status(404).json({ error: 'book not found' });

            if (req.body.contents) post[0].contents = req.body.contents;
            else console.log(req.body.contents);
            if (req.body.tag) post[0].tag = req.body.tag;
            if (req.body.date) post[0].date = req.body.date;
            post[0].save(function (err) {
                if (err) res.status(500).json({ error: 'failed to update' });
                res.json({ message: 'post updated' });
                console.log(post[0]);
            });

        });

    });

}