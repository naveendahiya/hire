(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateTagController', CandidateTagController);

    CandidateTagController.$inject = ['$scope', '$state', 'CandidateTag'];

    function CandidateTagController ($scope, $state, CandidateTag) {
        var vm = this;

        vm.candidateTags = [];

        loadAll();

        function loadAll() {
            CandidateTag.query(function(result) {
                vm.candidateTags = result;
            });
        }
    }
})();
