package main

import (
	"fmt"
)

var wrapError = &WrapError{}

type WrapError struct{}

func (w *WrapError) Error() string {
	return "wrap error"
}

func firstError() error {
	if err := secondError(); err != nil {
		return fmt.Errorf("second execute error: %w", err)
	}

	return nil
}

func secondError() error {
	return fmt.Errorf("error on secondError(). err=%w", &WrapError{})
}
