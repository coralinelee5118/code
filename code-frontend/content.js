// Store the selected text and image
let selectedText = '';
let selectedImage = null;

// Listen for text selection
document.addEventListener('mouseup', () => {
    const selection = window.getSelection();
    selectedText = selection.toString().trim();
    
    // Send the selected text to the popup
    chrome.runtime.sendMessage({
        type: 'TEXT_SELECTED',
        text: selectedText
    });
});

// Listen for image clicks
document.addEventListener('click', (e) => {
    if (e.target.tagName === 'IMG') {
        selectedImage = {
            src: e.target.src,
            alt: e.target.alt || '',
            width: e.target.naturalWidth,
            height: e.target.naturalHeight
        };
        
        // Send the selected image to the popup
        chrome.runtime.sendMessage({
            type: 'IMAGE_SELECTED',
            image: selectedImage
        });
    }
});

// Listen for messages from the popup
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.type === 'GET_SELECTED_TEXT') {
        sendResponse({ text: selectedText });
    } else if (request.type === 'GET_SELECTED_IMAGE') {
        sendResponse({ image: selectedImage });
    }
}); 