package dev.faiz.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Define a DTO class for the review request
    public static class ReviewRequest {
        private String reviewBody;
        private String imdbId;

        // Getters and Setters
        public String getReviewBody() {
            return reviewBody;
        }

        public void setReviewBody(String reviewBody) {
            this.reviewBody = reviewBody;
        }

        public String getImdbId() {
            return imdbId;
        }

        public void setImdbId(String imdbId) {
            this.imdbId = imdbId;
        }
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewRequest) {
        if (reviewRequest.getReviewBody() == null || reviewRequest.getReviewBody().isEmpty()) {
            return new ResponseEntity<>("Review body cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        if (reviewRequest.getImdbId() == null || reviewRequest.getImdbId().isEmpty()) {
            return new ResponseEntity<>("IMDB ID cannot be empty.", HttpStatus.BAD_REQUEST);
        }

        try {
            Review createdReview = reviewService.createReview(reviewRequest.getReviewBody(), reviewRequest.getImdbId());
            return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception (optional)
            return new ResponseEntity<>("An error occurred while creating the review.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
