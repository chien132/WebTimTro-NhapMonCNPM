<%@ page pageEncoding="utf-8"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="utf-8" />
            <title>Forgot Password</title>
            <link href="resources/css/styles.css" rel="stylesheet" />

        </head>

        <body id="homelogin">
            <div id="layoutAuthentication">
                <div id="layoutAuthentication_content">
                    <main>
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-lg-5">
                                    <div class="card shadow-lg border-0 rounded-lg mt-5">
                                        <div class="card-header">
                                            <h3 class="text-center font-weight-light my-4">Forgot Password
                                            </h3>
                                        </div>
                                        <div class="card-body">
                                            <form>
                                                <div class="form-group">
                                                    <label class="small mb-1" for="inputEmailAddress">Email</label>
                                                    <input class="form-control py-4" name="email" type="email" placeholder="Enter email" value="" required="required" /> <i style="color: red;">${message}</i>
                                                </div>
                                                <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0">
                                                    <button class="btn btn-primary" type="submit">Submit</button>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="card-footer text-center">
                                            <div class="small">
                                                <a href="login.htm">Remembered your account? Sign in!</a>
                                            </div>
                                            <div class="small">
                                                <a href="register.htm">Sign up another account</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
        </body>

        </html>