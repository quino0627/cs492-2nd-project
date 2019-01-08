// routes/index.js
let moment = require('moment');
module.exports = function (app, User) {
    //CREATE NEW USER
    app.post('/api/users/create', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, create new user");
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
                    console.log("complete!");
                    res.json({ result: 1 });
                });
            } else {
                console.log(users[0]);
                res.json({ result: 0 });
                console.log(moment().format("YYMMDDHHmmss") + "    OK")
            }
        })
    });
    //GET USER INFORMATION
    app.get('/api/users/info/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get user information")
        User.find({ user_id: req.params.user_id }, { user_id: 1, name: 1, phone: 1, friends: 1, _id: 0 }, function (err, user) {
            if (err) return res.status(500).send({ error: err });
            if (user.length === 0) {
                if (err) {
                    console.error(err);
                    res.json({ result: err });
                    return;
                }
                console.log("not exsited");
                res.json({ result: false });
            } else {
                // console.log(users[0]);
                res.json(user[0]);
                console.log(moment().format("YYMMDDHHmmss") + "    OK")
            }
        })
    })
    //LOGIN
    app.get('/api/users/login/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, login")
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
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
        })

    })
    //ADD FRIEND
    app.put('/api/users/following/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, add friend")
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
                // else res.json({ message: 'friends list updated' });
                console.log(err);
                res.json({result: true});
                
            });
            
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
        });

    });
    //DELETE FRIEND
    app.put('/api/users/delete_friend/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, delete friend")
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
                // else res.json({ message: 'friends list updated' });
                console.log(err);
                res.json({result: true});
            });
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
        });

    });
    //DELETE USER
    app.delete('/api/users/delete/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, delete user")
        User.remove({ user_id: req.params.user_id }, function (err, output) {
            if (err) return res.status(500).json({ error: "database failure" });
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
            /* ( SINCE DELETE OPERATION IS IDEMPOTENT, NO NEED TO SPECIFY )
            if(!output.result.n) return res.status(404).json({ error: "book not found" });
            res.json({ message: "book deleted" });
            */
            res.status(204).end();
        })
    });


    //GET ALL USERS
    app.get('/api/users/all/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get all users")
        User.find({"$nor" : [{user_id:req.params.user_id}]}, { user_id: 1, name: 1, phone: 1, friends: 1 }, function (err, users) {
            if (err) return res.status(500).send({ error: 'database failure' });
            res.json(users);
            console.log(moment().format("YYMMDDHHmmss") + "    OK")
        })
    });
    //GET ONE's FREINDS LIST
    app.get('/api/users/friends_list/:user_id', function (req, res) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get one's friends list")
        User.find({ user_id: req.params.user_id }, { friends: 1, _id: 0 }, function (err, users) {
            if (err) return res.status(500).send({ error: err });
            if (users.length === 0) return res.status(404).json({ error: 'user not found' });
            var userlist = [];
            var index = 1;
            var length = users[0].friends.length;
            // console.log(users[0].friends)
            User.find({ user_id: users[0].friends }, { user_id: 1, name: 1, phone: 1, friends: 1, _id: 0 }, function (err, user) {
                if (err) return res.status(500).send({ error: err });
                // console.log(user);
                userlist = user;
                res.json(userlist)
                console.log(moment().format("YYMMDDHHmmss") + "    OK")
            })
        })
    });

    //GET USERS BY PHONE
    app.post('/api/users/by_phone', function (req, res, next) {
        console.log(moment().format("YYMMDDHHmmss") +"    client request, get users by phone")

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
                if (count == check) {
                    res.json(result);
                    console.log(moment().format("YYMMDDHHmmss") + "    OK")
                }
            })
        })
        // User.find({ phone: req.body.phone }, { user_id: 1, name: 1 }, function (err, users) {
        //     if (err) return res.status(500).send({ error: err });
        //     if (users.length === 0) return res.status(404).json({ error: 'user not found' });
        //     res.json(users[0]);
        // })
    })

}