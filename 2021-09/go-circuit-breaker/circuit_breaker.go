package main

import (
	"log"
	"sync"
	"time"
)

type State int

const (
	Open  State = iota
	Close State = 1
)

type CircuitBreaker interface {
	Reset()
	RecordFailure()
	IsAvailable() bool
}

// FailureCountThresholdBreaker is circuit breaker. If state is close, circuit breaker is working. But state is open, breaker is not working.
// When some request is fail, the continuousFailureCount value increase.
// If continuousFailureCount is bigger than failureThreshold, state will be changed Open state.
// After resilienceWaitingTime, breaker state is half-open that try to request once to check remote call status is ok.
// If some request is ok, FailureCountThresholdBreaker state is close and reset failure count and time.
type FailureCountThresholdBreaker struct {
	mutex                  sync.Mutex
	name                   string
	state                  State
	failureThreshold       int32
	continuousFailureCount int32
	lastFailureTime        time.Time
	resilienceWaitingTime  time.Duration
}

func NewFailureCountThresholdBreaker(threshold int32, resilienceWaitingTime time.Duration) CircuitBreaker {
	return &FailureCountThresholdBreaker{
		mutex:                  sync.Mutex{},
		state:                  Close,
		failureThreshold:       threshold,
		continuousFailureCount: 0,
		lastFailureTime:        time.Now(),
		resilienceWaitingTime:  resilienceWaitingTime,
	}
}

func (b *FailureCountThresholdBreaker) Reset() {
	b.mutex.Lock()
	defer b.mutex.Unlock()

	b.state = Close
	b.continuousFailureCount = 0
	b.lastFailureTime = time.Time{}
}

func (b *FailureCountThresholdBreaker) RecordFailure() {
	b.mutex.Lock()
	defer b.mutex.Unlock()

	b.continuousFailureCount++
	b.lastFailureTime = time.Now()

	if b.continuousFailureCount >= b.failureThreshold {
		b.state = Open
	}
}

func (b *FailureCountThresholdBreaker) IsAvailable() bool {
	if b.state == Close {
		return true
	}

	if b.state == Open {
		// lastFailureTimeInterval = (now time) - (lastFailureTime)
		lastFailureTimeInterval := time.Now().Add(-time.Duration(b.lastFailureTime.UnixNano()))
		return time.Duration(lastFailureTimeInterval.UnixNano()) >= b.resilienceWaitingTime
	}

	log.Println("FailureCountThresholdBreaker state MUST be set. Close or Open")
	return true
}
