package main

import (
	"fmt"
	"time"

	"golang.org/x/time/rate"
)

func main() {
	// 1초 1개 허용. bucket size: 10
	limiter := rate.NewLimiter(rate.Limit(1), 5)

	r1 := limiter.Allow()
	fmt.Printf("request r1: %v\n", r1)

	r2 := limiter.Allow()
	fmt.Printf("request r2: %v\n", r2)

	r3 := limiter.Allow()
	fmt.Printf("request r3: %v\n", r3)

	r4 := limiter.Allow()
	fmt.Printf("request r4: %v\n", r4)

	r5 := limiter.Allow()
	fmt.Printf("request r5: %v\n", r5)

	r6 := limiter.Allow()
	fmt.Printf("request r6: %v\n", r6)

	time.Sleep(1 * time.Second)
	r7 := limiter.Allow()
	fmt.Printf("request r7: %v\n", r7)

	r8 := limiter.Allow()
	fmt.Printf("request r8: %v\n", r8)
}

/*
request r1: true
request r2: true
request r3: true
request r4: true
request r5: true
request r6: false
request r7: true
request r8: false
*/
