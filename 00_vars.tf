
variable "aws_region" {
  default = "eu-west-1"
}

data "aws_caller_identity" "current" {

}
