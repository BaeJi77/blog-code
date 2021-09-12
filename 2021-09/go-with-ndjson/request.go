package main

import (
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"strconv"
	"strings"

	"github.com/gin-gonic/gin"
)

type Request struct {
	Data       string `json:"Data"`
	HttpStatus string `json:"httpStatus"`
	Date       string `json:"Date"`
}

type Output struct{}

func NewOutput() *Output {
	return &Output{}
}

func (op *Output) Handle(c *gin.Context) {
	data := c.PostForm("data")
	date := c.PostForm("date")

	request := Request{Data: data, HttpStatus: strconv.Itoa(200), Date: date}
	f, _ := os.OpenFile("out.ndjson", os.O_CREATE|os.O_APPEND|os.O_RDWR, 0644)
	enc := json.NewEncoder(f)
	err := enc.Encode(request)
	if err != nil {
		return
	}

	c.JSON(200, request)
}

func (op *Output) GetData(c *gin.Context) {
	var stats []Request

	f, _ := ioutil.ReadFile("out.ndjson")
	dec := json.NewDecoder(strings.NewReader(string(f)))
	for {
		var v Request
		err := dec.Decode(&v)

		if err != nil {
			if err != io.EOF {
				fmt.Errorf("GetImonStats occuerd error. %s", err)
			}
			break
		}

		stats = append(stats, v)
	}

	c.JSON(200, stats)
}
