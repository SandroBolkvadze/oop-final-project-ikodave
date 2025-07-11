window.addEventListener('DOMContentLoaded', () => {
    const canvas = document.getElementById('background-canvas');
    const ctx = canvas.getContext('2d');

    function resize() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }
    window.addEventListener('resize', resize);
    resize();

    // ========== MOUSE ==========
    const mouse = { x: canvas.width / 2, y: canvas.height / 2 };
    document.addEventListener('mousemove', e => {
        mouse.x = e.clientX;
        mouse.y = e.clientY;
    });

    // ========== FACE ==========
    const face = {
        x: canvas.width / 2,
        y: canvas.height / 2,
        radius: 80,
        eyeOffsetX: 35,
        eyeOffsetY: 20,
        eyeRadius: 15,
        pupilRadius: 5
    };

    // ========== ANTS ==========
    const ants = [];
    for (let i = 0; i < 30; i++) {
        ants.push({
            x: Math.random() * canvas.width,
            y: Math.random() * canvas.height,
            dx: (Math.random() - 0.5) * 1,
            dy: (Math.random() - 0.5) * 1,
            size: 2 + Math.random() * 2
        });
    }

    function drawFace() {
        // head
        ctx.fillStyle = '#fff';
        ctx.beginPath();
        ctx.arc(face.x, face.y, face.radius, 0, Math.PI * 2);
        ctx.fill();

        // eyes
        [ -face.eyeOffsetX, face.eyeOffsetX ].forEach(offsetX => {
            const eyeX = face.x + offsetX;
            const eyeY = face.y - face.eyeOffsetY;
            // eye white
            ctx.fillStyle = '#000';
            ctx.beginPath();
            ctx.arc(eyeX, eyeY, face.eyeRadius, 0, Math.PI * 2);
            ctx.fill();

            // pupil
            const dx = mouse.x - eyeX;
            const dy = mouse.y - eyeY;
            const angle = Math.atan2(dy, dx);
            const maxDist = face.eyeRadius - face.pupilRadius - 2;
            const px = eyeX + Math.cos(angle) * maxDist * 0.5;
            const py = eyeY + Math.sin(angle) * maxDist * 0.5;

            // angry brow if close
            if (Math.hypot(dx, dy) < 50) {
                ctx.strokeStyle = '#f00';
                ctx.lineWidth = 3;
                ctx.beginPath();
                ctx.moveTo(eyeX - face.eyeRadius, eyeY - face.eyeRadius);
                ctx.lineTo(eyeX + face.eyeRadius, eyeY - face.eyeRadius / 2);
                ctx.stroke();
            }

            ctx.fillStyle = '#00c3ff';
            ctx.beginPath();
            ctx.arc(px, py, face.pupilRadius, 0, Math.PI * 2);
            ctx.fill();
        });
    }

    function updateAnts() {
        ants.forEach(ant => {
            ant.x += ant.dx;
            ant.y += ant.dy;
            ant.dx = Math.max(Math.min(ant.dx + (Math.random() - 0.5) * 0.2, 1), -1);
            ant.dy = Math.max(Math.min(ant.dy + (Math.random() - 0.5) * 0.2, 1), -1);
            if (ant.x < 0) ant.x = canvas.width;
            if (ant.x > canvas.width) ant.x = 0;
            if (ant.y < 0) ant.y = canvas.height;
            if (ant.y > canvas.height) ant.y = 0;
            ctx.fillStyle = '#fff';
            ctx.beginPath();
            ctx.arc(ant.x, ant.y, ant.size, 0, Math.PI * 2);
            ctx.fill();
        });
    }

    function animate() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        updateAnts();
        drawFace();
        requestAnimationFrame(animate);
    }

    animate();
});
