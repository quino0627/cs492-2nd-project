
module.exports = function (app, Post) {
    //CREATE NEW POST
    app.post('/api/posts/create', function (req, res) {
        var post = new Post();
        const fs = require('fs');
        let buff = new Buffer(req.body.pictureUrl, 'base64');
        fs.writeFileSync('./pictures/'+req.body.uploader+req.body.date+'.png', buff);
        post.pictureUrl = './pictures/'+req.body.uploader+req.body.date+'.png';

        post.uploader = req.body.uploader;
        post.date = req.body.date;
        post.contents = req.body.contents;
        post.tag = req.body.tag;
        post.post_id = req.body.uploader+req.body.date;

        post.save(function (err) {
            if (err) {
                console.error(err);
                res.json({ result: 0 });
                return;
            }
            console.log("create new post");
            res.json({ result: 1 });

        });

    })
    //GET ALL POSTS
    app.get('/api/posts/all', function (req, res) {
        Post.find(function (err, posts) {
            if (err) return res.status(500).send({ error: 'database failure' });
            res.json(posts);
     
            console.log(posts);
            console.log("client request getAllposts");
        })
    });
    //GET ONE's POSTS
    app.get('/api/posts/uploader/:uploader', function (req, res) {
        Post.find({ uploader: req.params.uploader }, { post_id: 1, pictureUrl: 1, date: 1, contents: 1, tag: 1 }, function (err, posts) {
            if (err) return res.status(500).json({ error: err });
            if (posts.length === 0) return res.status(404).json({ error: 'posts not found' });
            res.json(posts);
        })
    })
    //DELETE POST 
    app.delete('/api/posts/:post_id', function (req, res) {
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
    app.put('/api/posts/:posts_id', function (req, res) {
        Post.findById(req.params.post_id, function (err, post) {
            if (err) return res.status(500).json({ error: 'database failure' });
            if (!post) return res.status(404).json({ error: 'book not found' });

            if (req.body.contents) post.contents = req.body.contents;
            if (req.body.tag) post.tag = req.body.tag;
            post.save(function (err) {
                if (err) res.status(500).json({ error: 'failed to update' });
                res.json({ message: 'post updated' });
            });

        });

    });

}