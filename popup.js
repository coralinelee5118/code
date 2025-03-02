document.addEventListener('DOMContentLoaded', () => {
    const selectedTextDiv = document.getElementById('selected-text');
    const selectedImageDiv = document.getElementById('selected-image');
    const factResults = document.getElementById('fact-results');
    const imageResults = document.getElementById('image-results');
    const checkFactsButton = document.getElementById('check-facts');
    const checkImageButton = document.getElementById('check-image');
    const loadingDiv = document.getElementById('loading');

    let currentText = '';
    let currentImage = null;

    // Get the current tab
    function getCurrentTab() {
        return chrome.tabs.query({active: true, currentWindow: true});
    }

    // Update selected text
    function updateSelectedText(text) {
        currentText = text;
        if (text) {
            selectedTextDiv.innerHTML = `<p>${text.substring(0, 150)}${text.length > 150 ? '...' : ''}</p>`;
            checkFactsButton.disabled = false;
        } else {
            selectedTextDiv.innerHTML = '<p>No text selected. Select text on the page to fact-check.</p>';
            checkFactsButton.disabled = true;
        }
    }

    // Update selected image
    function updateSelectedImage(image) {
        currentImage = image;
        if (image) {
            selectedImageDiv.innerHTML = `
                <img src="${image.src}" alt="${image.alt}" style="max-width: 100%; max-height: 150px;">
                <p>${image.alt || 'No description available'}</p>
            `;
            checkImageButton.disabled = false;
        } else {
            selectedImageDiv.innerHTML = '<p>No image selected. Click on an image to analyze.</p>';
            checkImageButton.disabled = true;
        }
    }

    // Display fact check results
    function displayFactResults(results) {
        if (!results || !results.claims || results.claims.length === 0) {
            factResults.innerHTML = '<div class="result-item">No fact check results found for this text.</div>';
            return;
        }

        const resultsHtml = results.claims.map(claim => `
            <div class="result-item">
                <h3>${claim.text}</h3>
                <p><strong>Claim Review:</strong> ${claim.claimReview[0].textualRating}</p>
                <p><strong>Source:</strong> ${claim.claimReview[0].publisher.name}</p>
                <p><a href="${claim.claimReview[0].url}" target="_blank">Read full fact check</a></p>
            </div>
        `).join('');

        factResults.innerHTML = resultsHtml;
    }

    // Display image search results
    function displayImageResults(results) {
        if (!results || !results.items || results.items.length === 0) {
            imageResults.innerHTML = '<div class="result-item">No similar images found.</div>';
            return;
        }

        const resultsHtml = results.items.slice(0, 5).map(item => `
            <div class="result-item">
                <h3>${item.title}</h3>
                <p>${item.snippet}</p>
                <p><a href="${item.link}" target="_blank">View source</a></p>
            </div>
        `).join('');

        imageResults.innerHTML = resultsHtml;
    }

    // Show/hide loading indicator
    function toggleLoading(show) {
        loadingDiv.classList.toggle('hidden', !show);
    }

    // Event listeners
    checkFactsButton.addEventListener('click', () => {
        if (!currentText) return;
        
        toggleLoading(true);
        chrome.runtime.sendMessage({
            type: 'CHECK_FACTS',
            text: currentText
        });
    });

    checkImageButton.addEventListener('click', () => {
        if (!currentImage) return;
        
        toggleLoading(true);
        chrome.runtime.sendMessage({
            type: 'SEARCH_IMAGE',
            imageUrl: currentImage.src
        });
    });

    // Listen for messages from content script and background script
    chrome.runtime.onMessage.addListener((message) => {
        switch (message.type) {
            case 'TEXT_SELECTED':
                updateSelectedText(message.text);
                break;
            case 'IMAGE_SELECTED':
                updateSelectedImage(message.image);
                break;
            case 'FACT_CHECK_RESULT':
                toggleLoading(false);
                displayFactResults(message.result);
                break;
            case 'IMAGE_SEARCH_RESULT':
                toggleLoading(false);
                displayImageResults(message.result);
                break;
            case 'ERROR':
                toggleLoading(false);
                factResults.innerHTML = `<div class="error">Error: ${message.error}</div>`;
                break;
        }
    });

    // Initialize: get any existing selected text or image
    getCurrentTab().then(([tab]) => {
        chrome.tabs.sendMessage(tab.id, { type: 'GET_SELECTED_TEXT' }, (response) => {
            if (response && response.text) {
                updateSelectedText(response.text);
            }
        });
        
        chrome.tabs.sendMessage(tab.id, { type: 'GET_SELECTED_IMAGE' }, (response) => {
            if (response && response.image) {
                updateSelectedImage(response.image);
            }
        });
    });
}); 