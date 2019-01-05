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
            if (users.length === 0) {
                user.save(function (err) {
                    if (err) {
                        console.error(err);
                        res.json({ result: 0 });
                        return;
                    }
                    console.log("creat new user");
                    res.json({ result: 1 });
                });
            } else {
                console.log(users[0]);
                res.json({ result: 0 });
            }
        })
    });
    //LOGIN
    app.get('/api/users/login/:user_id', function (req, res) {
        User.find({ user_id: req.params.user_id }, { user_id: 1 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length === 0) {
                if (err) {
                    console.error(err);
                    res.json({ result: err });
                    return;
                }
                console.log("first login");
                res.json({ result: false });
            } else {
                console.log(users[0]);
                res.json({ result: true });
            }

        })

    })
    //ADD FRIEND
    app.put('/api/users/following/:user_id', function (req, res) {
        User.find({ user_id: req.params.user_id }, { friends: 1, _id: 1 }, function (err, user) {
            if (err) return res.status(500).json({ error: 'database failure' });
            if (!user) return res.status(404).json({ error: 'book not found' });
            if (req.body.user_id) {
                console.log(user);
                user[0].friends = user[0].friends.concat(req.body.user_id);
                console.log(user);
            }
            user[0].save(function (err) {
                if (err) res.status(500).json({ error: 'failed to update' });
                else res.json({ message: 'friends list updated' });
                console.log(err);
            });

        });

    });
    //DELETE FRIEND
    app.delete('/api/users/delete/:user_id', function (req, res) {
        User.find({ user_id: req.params.user_id }, { friends: 1, _id: 1 }, function (err, user) {
            if (err) return res.status(500).json({ error: 'database failure' });
            if (!user) return res.status(404).json({ error: 'book not found' });
            if (req.body.user_id) {
                console.log(user);
                var index = user[0].friends.indexOf(req.body.user_id);
                user[0].friends.splice(index, 1);
                console.log(user);
            }
            user[0].save(function (err) {
                if (err) res.status(500).json({ error: 'failed to update' });
                else res.json({ message: 'friends list updated' });
                console.log(err);
            });

        });

    });
    //DELETE USER
    app.delete('/api/users/delete/:user_id', function (req, res) {
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
        User.find(null, { user_id: 1, name: 1, phone: 1, friends: 1 }, function (err, users) {
            if (err) return res.status(500).send({ error: 'database failure' });
            res.json(users);
            console.log(res.toString() + "client request getAllUsers")
        })
    });
    //GET ONE's FREINDS LIST
    app.get('/api/users/friends_list/:user_id', function (req, res) {
        User.find({ user_id: req.params.user_id }, { friends: 1, _id: 0 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length === 0) return res.status(404).json({ error: 'user not found' });
            res.json(users[0]);
        })
    });

    //GET USERS BY PHONE
    app.post('/api/users/by_phone', function (req, res, next) {

        console.log(req.body.phone);
        result = [];
        var count = 0;
        var check = req.body.phone.length;
        req.body.phone.forEach(element => {
            User.find({ phone: element }, { user_id: 1, name: 1 }, function (err, users) {
                if (err) return res.status(500).send({ error: err });
                // if (users.length === 0) return res.status(404).json({ error: 'user not found' });
                result = result.concat(users);
                count++;
                if(count==check)
                    res.json(result);
            })
        })


        // User.find({ phone: req.body.phone }, { user_id: 1, name: 1 }, function (err, users) {
        //     if (err) return res.status(500).send({ error: err });
        //     if (users.length === 0) return res.status(404).json({ error: 'user not found' });
        //     res.json(users[0]);
        // })
    })

}