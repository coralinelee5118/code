// Constants for API endpoints
const FACT_CHECK_API_BASE = 'https://factchecktools.googleapis.com/v1alpha1/claims:search';
const GOOGLE_SEARCH_API_BASE = 'https://customsearch.googleapis.com/customsearch/v1';
const API_KEY = 'AIzaSyC4ebQ8p1EnzizuUFoq939_AP0f_DnnJdE';

// Function to check facts using Google Fact Check API
async function checkFacts(query) {
    try {
        const response = await fetch(`${FACT_CHECK_API_BASE}?query=${encodeURIComponent(query)}&key=${API_KEY}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error checking facts:', error);
        throw error;
    }
}

// Function to perform reverse image search
async function searchImage(imageUrl) {
    try {
        const response = await fetch(`${GOOGLE_SEARCH_API_BASE}?key=${API_KEY}&cx=YOUR_SEARCH_ENGINE_ID&searchType=image&q=${encodeURIComponent(imageUrl)}`);
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error searching image:', error);
        throw error;
    }
}

// Listen for messages from popup or content script
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.type === 'CHECK_FACTS') {
        checkFacts(request.text)
            .then(result => {
                chrome.runtime.sendMessage({
                    type: 'FACT_CHECK_RESULT',
                    result: result
                });
            })
            .catch(error => {
                chrome.runtime.sendMessage({
                    type: 'ERROR',
                    error: error.message
                });
            });
    } else if (request.type === 'SEARCH_IMAGE') {
        searchImage(request.imageUrl)
            .then(result => {
                chrome.runtime.sendMessage({
                    type: 'IMAGE_SEARCH_RESULT',
                    result: result
                });
            })
            .catch(error => {
                chrome.runtime.sendMessage({
                    type: 'ERROR',
                    error: error.message
                });
            });
    }
    
    // Return true to indicate that we will send a response asynchronously
    return true;
}); 