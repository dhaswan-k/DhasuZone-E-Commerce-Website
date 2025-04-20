// DhasuZone E-commerce JavaScript

// DOM Elements
document.addEventListener('DOMContentLoaded', function() {
    // Initialize components
    initNavbar();
    initProductGallery();
    initQuantityInputs();
    initProductTabs();
    initChatbot();
    initSliders();
    initAnimations();
    initCountdown();
});

// Navigation
function initNavbar() {
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarNav = document.querySelector('.navbar-nav');
    
    if (navbarToggler && navbarNav) {
        navbarToggler.addEventListener('click', function() {
            navbarNav.classList.toggle('active');
        });
    }
}

// Product Gallery
function initProductGallery() {
    const thumbnails = document.querySelectorAll('.product-thumbnail');
    const mainImage = document.querySelector('.product-main-image img');
    
    if (thumbnails.length && mainImage) {
        thumbnails.forEach(thumbnail => {
            thumbnail.addEventListener('click', function() {
                const src = this.querySelector('img').getAttribute('src');
                mainImage.setAttribute('src', src);
                
                // Remove active class from all thumbnails
                thumbnails.forEach(thumb => thumb.classList.remove('active'));
                
                // Add active class to clicked thumbnail
                this.classList.add('active');
            });
        });
    }
}

// Quantity Inputs
function initQuantityInputs() {
    const quantityInputs = document.querySelectorAll('.quantity-input');
    
    quantityInputs.forEach(input => {
        const decreaseBtn = input.querySelector('.quantity-btn:first-child');
        const increaseBtn = input.querySelector('.quantity-btn:last-child');
        const valueEl = input.querySelector('.quantity-value');
        
        if (decreaseBtn && increaseBtn && valueEl) {
            decreaseBtn.addEventListener('click', function() {
                let value = parseInt(valueEl.value);
                if (value > 1) {
                    valueEl.value = value - 1;
                }
            });
            
            increaseBtn.addEventListener('click', function() {
                let value = parseInt(valueEl.value);
                valueEl.value = value + 1;
            });
        }
    });
}

// Product Tabs
function initProductTabs() {
    const tabLinks = document.querySelectorAll('.tab-link');
    const tabContents = document.querySelectorAll('.tab-content');
    
    if (tabLinks.length && tabContents.length) {
        tabLinks.forEach(link => {
            link.addEventListener('click', function() {
                const tabId = this.getAttribute('data-tab');
                
                // Remove active class from all tabs
                tabLinks.forEach(tab => tab.classList.remove('active'));
                tabContents.forEach(content => content.classList.remove('active'));
                
                // Add active class to clicked tab and corresponding content
                this.classList.add('active');
                document.getElementById(tabId).classList.add('active');
            });
        });
    }
}

// Chatbot
function initChatbot() {
    const chatbotToggle = document.querySelector('.chatbot-toggle');
    const chatbotWindow = document.querySelector('.chatbot-window');
    const chatbotClose = document.querySelector('.chatbot-close');
    const chatbotInput = document.querySelector('.chatbot-input input');
    const chatbotSubmit = document.querySelector('.chatbot-input button');
    const chatbotMessages = document.querySelector('.chatbot-messages');
    
    if (chatbotToggle && chatbotWindow) {
        // Toggle chatbot window
        chatbotToggle.addEventListener('click', function() {
            chatbotWindow.classList.toggle('active');
            if (chatbotWindow.classList.contains('active')) {
                chatbotInput.focus();
                
                // Add welcome message if chat is empty
                if (chatbotMessages.children.length === 0) {
                    addBotMessage("Hi there! I'm DhasuBot, your shopping assistant. How can I help you today?");
                    addSuggestedResponses([
                        "What are the featured products?",
                        "Show me products on sale",
                        "How do I track my order?",
                        "Tell me about shipping"
                    ]);
                }
            }
        });
        
        // Close chatbot window
        if (chatbotClose) {
            chatbotClose.addEventListener('click', function() {
                chatbotWindow.classList.remove('active');
            });
        }
        
        // Submit message
        if (chatbotSubmit && chatbotInput) {
            chatbotSubmit.addEventListener('click', sendMessage);
            
            chatbotInput.addEventListener('keypress', function(e) {
                if (e.key === 'Enter') {
                    sendMessage();
                }
            });
        }
    }
    
    function sendMessage() {
        if (chatbotInput.value.trim() === '') return;
        
        const message = chatbotInput.value.trim();
        addUserMessage(message);
        chatbotInput.value = '';
        
        // Process the message
        processChatbotMessage(message);
    }
    
    function addUserMessage(message) {
        const messageEl = document.createElement('div');
        messageEl.classList.add('message', 'message-user');
        messageEl.textContent = message;
        chatbotMessages.appendChild(messageEl);
        scrollToBottom();
    }
    
    function addBotMessage(message) {
        const messageEl = document.createElement('div');
        messageEl.classList.add('message', 'message-bot');
        messageEl.textContent = message;
        chatbotMessages.appendChild(messageEl);
        scrollToBottom();
    }
    
    function addSuggestedResponses(responses) {
        const suggestedEl = document.createElement('div');
        suggestedEl.classList.add('suggested-responses');
        
        responses.forEach(response => {
            const responseEl = document.createElement('div');
            responseEl.classList.add('suggested-response');
            responseEl.textContent = response;
            responseEl.addEventListener('click', function() {
                addUserMessage(response);
                processChatbotMessage(response);
            });
            suggestedEl.appendChild(responseEl);
        });
        
        chatbotMessages.appendChild(suggestedEl);
        scrollToBottom();
    }
    
    function processChatbotMessage(message) {
        // Simulate loading
        setTimeout(() => {
            // In a real implementation, this would call the server
            // For demo purposes, we'll use a simple mapping
            
            const lowerMessage = message.toLowerCase();
            
            if (lowerMessage.includes('featured') || lowerMessage.includes('best')) {
                addBotMessage("Here are our featured products! They're our most popular items.");
                // In a real implementation, we would show featured products
                addSuggestedResponses(["Show me products on sale", "What categories do you have?"]);
            } 
            else if (lowerMessage.includes('sale') || lowerMessage.includes('discount') || lowerMessage.includes('offer')) {
                addBotMessage("We have some great deals running right now! Check out our sale section for discounts up to 50% off.");
                // In a real implementation, we would show sale products
                addSuggestedResponses(["Show me featured products", "What's your return policy?"]);
            }
            else if (lowerMessage.includes('track') || lowerMessage.includes('order status')) {
                addBotMessage("You can track your order in the My Account section under 'My Orders'. You'll need to be logged in to access this information.");
                addSuggestedResponses(["What's your return policy?", "How long does shipping take?"]);
            }
            else if (lowerMessage.includes('shipping') || lowerMessage.includes('delivery')) {
                addBotMessage("We offer free shipping on all orders over ₹499. Standard delivery takes 3-5 business days. Express delivery (1-2 days) is available for an additional fee.");
                addSuggestedResponses(["What's your return policy?", "Do you ship internationally?"]);
            }
            else if (lowerMessage.includes('return') || lowerMessage.includes('refund')) {
                addBotMessage("We have a 30-day return policy. If you're not satisfied with your purchase, you can return it within 30 days for a full refund. The item must be in its original condition and packaging.");
                addSuggestedResponses(["How do I return an item?", "What payment methods do you accept?"]);
            }
            else if (lowerMessage.includes('payment') || lowerMessage.includes('pay')) {
                addBotMessage("We accept all major credit/debit cards, net banking, UPI, and wallet payments through Razorpay. All transactions are secure and encrypted.");
                addSuggestedResponses(["Is COD available?", "Do you have any discounts?"]);
            }
            else if (lowerMessage.includes('contact') || lowerMessage.includes('customer service') || lowerMessage.includes('help')) {
                addBotMessage("You can reach our customer service team at support@dhasuzone.com or call us at 1800-123-4567. Our support hours are 9am to 6pm, Monday to Saturday.");
                addSuggestedResponses(["What's your return policy?", "How do I track my order?"]);
            }
            else if (lowerMessage.includes('category') || lowerMessage.includes('categories')) {
                addBotMessage("We have products in the following categories: Electronics, Clothing, Books, Home & Kitchen, and Sports & Outdoors.");
                addSuggestedResponses(["Show me electronics", "Show me clothing", "Show me books"]);
            }
            else {
                addBotMessage("I'm not sure I understand. Would you like to browse our featured products or check out our current sales?");
                addSuggestedResponses([
                    "Show me featured products", 
                    "What are your bestsellers?", 
                    "Products on sale", 
                    "Contact customer service"
                ]);
            }
        }, 600);
    }
    
    function scrollToBottom() {
        chatbotMessages.scrollTop = chatbotMessages.scrollHeight;
    }
}

// Image Sliders
function initSliders() {
    // This would be implemented with a slider library in a production environment
    // For demo purposes, we'll just handle basic functionality
    const sliders = document.querySelectorAll('.slider');
    
    sliders.forEach(slider => {
        const slidesContainer = slider.querySelector('.slides');
        const slides = slider.querySelectorAll('.slide');
        const prevBtn = slider.querySelector('.slider-prev');
        const nextBtn = slider.querySelector('.slider-next');
        const dots = slider.querySelectorAll('.slider-dot');
        
        if (slidesContainer && slides.length && prevBtn && nextBtn) {
            let currentIndex = 0;
            
            // Initialize
            updateSlider();
            
            // Previous slide
            prevBtn.addEventListener('click', function() {
                currentIndex = (currentIndex > 0) ? currentIndex - 1 : slides.length - 1;
                updateSlider();
            });
            
            // Next slide
            nextBtn.addEventListener('click', function() {
                currentIndex = (currentIndex < slides.length - 1) ? currentIndex + 1 : 0;
                updateSlider();
            });
            
            // Dots navigation
            if (dots.length) {
                dots.forEach((dot, index) => {
                    dot.addEventListener('click', function() {
                        currentIndex = index;
                        updateSlider();
                    });
                });
            }
            
            function updateSlider() {
                // Update slides
                const offset = -currentIndex * 100;
                slidesContainer.style.transform = `translateX(${offset}%)`;
                
                // Update dots
                if (dots.length) {
                    dots.forEach((dot, index) => {
                        dot.classList.toggle('active', index === currentIndex);
                    });
                }
            }
            
            // Auto slide (if enabled)
            if (slider.hasAttribute('data-auto-slide')) {
                const interval = parseInt(slider.getAttribute('data-auto-slide')) || 5000;
                
                setInterval(function() {
                    currentIndex = (currentIndex < slides.length - 1) ? currentIndex + 1 : 0;
                    updateSlider();
                }, interval);
            }
        }
    });
}

// Animations
function initAnimations() {
    // Animate elements when they come into view
    const animateElements = document.querySelectorAll('[data-animate]');
    
    if (animateElements.length) {
        // Check if element is in viewport
        function isInViewport(element) {
            const rect = element.getBoundingClientRect();
            return (
                rect.top <= (window.innerHeight || document.documentElement.clientHeight) * 0.85 &&
                rect.bottom >= 0
            );
        }
        
        // Check elements on scroll
        function checkElements() {
            animateElements.forEach(element => {
                if (isInViewport(element) && !element.classList.contains('animated')) {
                    const animation = element.getAttribute('data-animate');
                    element.classList.add(animation, 'animated');
                }
            });
        }
        
        // Initial check
        checkElements();
        
        // Check on scroll
        window.addEventListener('scroll', checkElements);
    }
}

// Countdown Timer
function initCountdown() {
    const countdownElements = document.querySelectorAll('[data-countdown]');
    
    countdownElements.forEach(element => {
        const targetDate = new Date(element.getAttribute('data-countdown')).getTime();
        
        // Update countdown every second
        const countdownInterval = setInterval(function() {
            const now = new Date().getTime();
            const distance = targetDate - now;
            
            // Time calculations
            const days = Math.floor(distance / (1000 * 60 * 60 * 24));
            const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
            const seconds = Math.floor((distance % (1000 * 60)) / 1000);
            
            // Update elements
            const daysEl = element.querySelector('.days');
            const hoursEl = element.querySelector('.hours');
            const minutesEl = element.querySelector('.minutes');
            const secondsEl = element.querySelector('.seconds');
            
            if (daysEl) daysEl.textContent = days.toString().padStart(2, '0');
            if (hoursEl) hoursEl.textContent = hours.toString().padStart(2, '0');
            if (minutesEl) minutesEl.textContent = minutes.toString().padStart(2, '0');
            if (secondsEl) secondsEl.textContent = seconds.toString().padStart(2, '0');
            
            // If countdown is finished
            if (distance < 0) {
                clearInterval(countdownInterval);
                element.innerHTML = '<p>This offer has expired!</p>';
            }
        }, 1000);
    });
}

// Add to Cart
function addToCart(productId, quantity = 1) {
    // In a real implementation, this would call the server
    // For demo purposes, we'll just show a notification
    
    const notification = document.createElement('div');
    notification.classList.add('notification', 'notification-success');
    notification.innerHTML = `
        <div class="notification-icon">
            <i class="fas fa-check-circle"></i>
        </div>
        <div class="notification-content">
            <p>Product added to cart!</p>
        </div>
    `;
    
    document.body.appendChild(notification);
    
    // Remove notification after 3 seconds
    setTimeout(function() {
        notification.classList.add('fade-out');
        setTimeout(function() {
            notification.remove();
        }, 300);
    }, 3000);
}

// Add to Wishlist
function addToWishlist(productId) {
    // In a real implementation, this would call the server
    // For demo purposes, we'll just toggle the icon
    
    const wishlistBtn = document.querySelector(`.product-wishlist[data-product-id="${productId}"]`);
    
    if (wishlistBtn) {
        wishlistBtn.classList.toggle('active');
        
        const notification = document.createElement('div');
        notification.classList.add('notification', 'notification-info');
        
        if (wishlistBtn.classList.contains('active')) {
            notification.innerHTML = `
                <div class="notification-icon">
                    <i class="fas fa-heart"></i>
                </div>
                <div class="notification-content">
                    <p>Product added to wishlist!</p>
                </div>
            `;
        } else {
            notification.innerHTML = `
                <div class="notification-icon">
                    <i class="fas fa-heart-broken"></i>
                </div>
                <div class="notification-content">
                    <p>Product removed from wishlist!</p>
                </div>
            `;
        }
        
        document.body.appendChild(notification);
        
        // Remove notification after 3 seconds
        setTimeout(function() {
            notification.classList.add('fade-out');
            setTimeout(function() {
                notification.remove();
            }, 300);
        }, 3000);
    }
}