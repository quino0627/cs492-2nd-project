// routes/index.js
module.exports = function (app, User) {
    //CREATE NEW USER
    app.post('/api/users/create', function (req, res) {
        var user = new User();
        user.name = req.body.name;
        user.phone = req.body.phone;
        user.user_id = req.body.user_id;
        user.friends = req.body.friends;

        User.find({ user_id: user.user_id }, { user_id: 1 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length===0) {
                user.save(function (err) {
                    if (err) {
                        console.error(err);
                        res.json({ result: 0 });
                        return;
                    }
                    console.log("creat new user");
                    res.json({ result: 1 });

                });
            } else{
                console.log(users[0]);
                res.json({ result: 0 });
            }

        })


    });

    //ADD FRIEND
    app.put('/api/users/add_friend/:user_id', function (req, res) {
        User.findById(req.params.user_id, function (err, user) {
            if (err) return res.status(500).json({ error: 'database failure' });
            if (!user) return res.status(404).json({ error: 'book not found' });
            if (req.body.friends) user.friends = user.friends + req.body.friends;
            user.save(function (err) {
                if (err) res.status(500).json({ error: 'failed to update' });
                res.json({ message: 'friends list updated' });
            });

        });

    });
    //DELETE FRIEND
    app.delete('/api/users/:user_id', function (req, res) {
        User.remove({ user_id: req.params.user_id }, function (err, output) {
            if (err) return res.status(500).json({ error: "database failure" });

            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "book not found" });
            res.json({ message: "book deleted" });
            */
            res.status(204).end();
        })
    });

    //GET ALL USERS
    app.get('/api/users/all', function (req, res) {
        User.find(null, { user_id:1, name:1, phone:1, friends:1},function (err, users) {
            if (err) return res.status(500).send({ error: 'database failure' });
            res.json(users);
            console.log(res.toString() + "client request getAllUsers")
        })
    });
    //GET ONE's FREINDS LIST
    app.get('/api/users/friend_list/:user_id', function (req, res) {
        User.find({ user_id: req.params.user_id }, { friends: 1, _id:0 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length === 0) return res.status(404).json({ error: 'user not found' });
            res.json(users[0]);
        })
    });

}