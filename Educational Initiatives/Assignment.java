public class Assignment {
    private String details;
    private boolean submitted;

    public Assignment(String details) {
        this.details = details;
        this.submitted = false;
    }

    public String getDetails() {
        return details;
    }

    public void markAsSubmitted() {
        this.submitted = true;
    }

    public boolean isSubmitted() {
        return submitted;
    }
}
