
resource "aws_iam_user" "devternity_lambda" {
  name = "devternity_lambda"
}

resource "aws_iam_user_policy_attachment" "devternity_lambda_policy_attachment" {
  user       = "${aws_iam_user.devternity_lambda.name}"
  policy_arn = "arn:aws:iam::aws:policy/AWSLambdaFullAccess"
}
