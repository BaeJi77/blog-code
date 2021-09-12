package main

import (
	"fmt"

	"github.com/gin-gonic/gin"
)

const (
	PortNumber = 8080
)

func main() {
	op := NewOutput()

	r := gin.Default()
	r.GET("/data", op.GetData)
	r.POST("/store", op.Handle)

	r.Run(fmt.Sprintf(":%d", 8080))
}
